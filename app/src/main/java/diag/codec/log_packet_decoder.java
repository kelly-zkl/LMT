package diag.codec;

import android.os.Bundle;
import android.os.Message;
import android.util.Log;

import java.util.ArrayList;

import diag.binary_dumper;
import diag.codec.ml1.log_lte_initial_acquisition_results;
import diag.codec.ml1.log_lte_ml1_connected_mode_lte_intrafreq_meas_results;
import diag.codec.ml1.log_lte_ml1_connected_neighbor_cell_meas_request_response;
import diag.codec.ml1.log_lte_ml1_idle_measurement_request;
import diag.codec.ml1.log_lte_ml1_idle_neighbor_cell_meas_request_response;
import diag.codec.ml1.log_lte_ml1_idle_serving_cell_meas_response;
import diag.codec.ml1.log_lte_ml1_intra_frequency_cell_reselection;
import diag.codec.ml1.log_lte_ml1_neighbor_measurements;
import diag.codec.ml1.log_lte_ml1_reselection_candidates;
import diag.codec.ml1.log_lte_ml1_s_criteria_check_procedure;
import diag.codec.ml1.log_lte_ml1_serving_cell_information;
import diag.codec.ml1.log_lte_ml1_serving_cell_meas_and_eval;
import diag.codec.ml1.log_lte_ml1_ue_mobility_state_change;
import diag.codec.ml1.msg1;
import diag.codec.ml1.msg2;
import diag.codec.ml1.msg3;
import diag.codec.ml1.msg4;
import diag.codec.nas.log_lte_nas_emm_forbidden_tai_list;
import diag.codec.nas.log_lte_nas_emm_plain_ota_incoming_msg;
import diag.codec.nas.log_lte_nas_emm_plain_ota_outcoming_msg;
import diag.codec.nas.log_lte_nas_emm_state;
import diag.codec.nas.log_lte_nas_esm_plain_ota_incoming_msg;
import diag.codec.nas.log_lte_nas_esm_plain_ota_outgoing_msg;
import diag.codec.rrc.log_lte_plmn_search_request;
import diag.codec.rrc.log_lte_plmn_search_response;
import diag.codec.rrc.log_lte_rrc_log_meas_info;
import diag.codec.rrc.log_lte_rrc_mib;
import diag.codec.rrc.log_lte_rrc_ota;
import diag.codec.rrc.log_lte_rrc_paging_ue;
import diag.codec.rrc.log_lte_rrc_serving_cell_info;
import ui.ui_messager;

import static diag.codec.log_packet_code.LOG_LTE_Contention_Resolution_Message_Report;
import static diag.codec.log_packet_code.LOG_LTE_Initial_Acquisition_Results;
import static diag.codec.log_packet_code.LOG_LTE_ML1_Connected_Mode_LTE_IntraFreq_Meas_Results;
import static diag.codec.log_packet_code.LOG_LTE_ML1_Connected_Neighbor_Meas_Request_Response;
import static diag.codec.log_packet_code.LOG_LTE_ML1_Idle_Measurement_Request;
import static diag.codec.log_packet_code.LOG_LTE_ML1_Idle_Neighbor_Cell_Meas_Request_Response;
import static diag.codec.log_packet_code.LOG_LTE_ML1_Idle_Serving_Cell_Meas_Response;
import static diag.codec.log_packet_code.LOG_LTE_ML1_Intra_Frequency_Cell_Reselection;
import static diag.codec.log_packet_code.LOG_LTE_ML1_Neighbor_Measurements;
import static diag.codec.log_packet_code.LOG_LTE_ML1_Reselection_Candidates;
import static diag.codec.log_packet_code.LOG_LTE_ML1_S_Criteria_Check_Procedure;
import static diag.codec.log_packet_code.LOG_LTE_ML1_Serving_Cell_Information;
import static diag.codec.log_packet_code.LOG_LTE_ML1_Serving_Cell_Meas_and_Eval;
import static diag.codec.log_packet_code.LOG_LTE_ML1_UE_Mobility_State_change;
import static diag.codec.log_packet_code.LOG_LTE_NAS_EMM_Forbidden_TAI_List;
import static diag.codec.log_packet_code.LOG_LTE_NAS_EMM_Plain_OTA_Incoming_Msg;
import static diag.codec.log_packet_code.LOG_LTE_NAS_EMM_Plain_OTA_Outgoing_Msg;
import static diag.codec.log_packet_code.LOG_LTE_NAS_EMM_State;
import static diag.codec.log_packet_code.LOG_LTE_NAS_ESM_Plain_OTA_Incoming_Msg;
import static diag.codec.log_packet_code.LOG_LTE_NAS_ESM_Plain_OTA_Outgoing_Msg;
import static diag.codec.log_packet_code.LOG_LTE_NAS_MAX;
import static diag.codec.log_packet_code.LOG_LTE_PLMN_Search_Request;
import static diag.codec.log_packet_code.LOG_LTE_PLMN_Search_Response;
import static diag.codec.log_packet_code.LOG_LTE_RRC_Log_Meas_Info;
import static diag.codec.log_packet_code.LOG_LTE_RRC_MAX;
import static diag.codec.log_packet_code.LOG_LTE_RRC_MIB;
import static diag.codec.log_packet_code.LOG_LTE_RRC_OTA;
import static diag.codec.log_packet_code.LOG_LTE_RRC_Paging_UE;
import static diag.codec.log_packet_code.LOG_LTE_RRC_Serving_Cell_Info;
import static diag.codec.log_packet_code.LOG_LTE_Random_Access_Request_Report;
import static diag.codec.log_packet_code.LOG_LTE_Random_Access_Response_Report;
import static diag.codec.log_packet_code.LOG_LTE_UE_Identification_Message_Report;
import static ui.ui_messager.ui_msg_diag_ind;

public class log_packet_decoder {

    static private int req_code;
    static private int rsp_code;
    static private int rsp_len;
    static private byte cmd_code;
    static private short more;
    static private short cmd_len;
    static private short log_len;
    static private int log_code;
    static private long time_stamp;

    public static boolean is_debug_on = false;
    private static int packet_num = 0;

    static private binary_decoder decoder = new binary_decoder();

    static public void decode(byte[] cmd_rsp) {

        decoder.set_buff(cmd_rsp);

        req_code = decoder.getIntValue();
        rsp_code = decoder.getIntValue();
        rsp_len = decoder.getIntValue();
        cmd_code = decoder.getByteValue();
        more = decoder.getByteValue();
        cmd_len = decoder.getShortValue();
        log_len = decoder.getShortValue();
        log_code = decoder.getShortValue();
        log_code = log_code & 0x0000ffff;
        time_stamp = decoder.getLongValue();

        if (is_debug_on) {
            System.out.println("---- packet: " + packet_num + " len: " + rsp_len + " ----");
            System.out.println("req_code: " + req_code + " rsp_ok: " + rsp_code);
            System.out.printf("log_code: 0x%04x\n", log_code);
            binary_dumper.dump(cmd_rsp, rsp_len);
        }

        log_packet packet = new log_packet(log_code, log_len, decoder);

        if (log_code < LOG_LTE_RRC_MAX) {
            decode_rrc(packet);
        } else if (log_code < LOG_LTE_NAS_MAX) {
            decode_nas(packet);
        } else {
            decode_ml1(packet);
        }

        notity_ui(packet);
    }

    static private void notity_ui(log_packet packet) {

        Message msg = new Message();
        Bundle bundle = new Bundle();
        ArrayList list = new ArrayList();

        list.add(packet);
        bundle.putParcelableArrayList("list", list);
        msg.what = ui_msg_diag_ind; // update ui
        msg.arg1 = log_code;
        msg.setData(bundle);
        ui_messager.notify(msg);
    }

    static public void decode_rrc(log_packet packet) {

        switch (log_code) {
            case LOG_LTE_RRC_OTA:
                log_lte_rrc_ota.decode(packet);
                break;
            case LOG_LTE_RRC_MIB:
                log_lte_rrc_mib.decode(packet);
                break;
            case LOG_LTE_RRC_Serving_Cell_Info:
                log_lte_rrc_serving_cell_info.decode(packet);
                break;
            case LOG_LTE_PLMN_Search_Request:
                log_lte_plmn_search_request.decode(packet);
                break;
            case LOG_LTE_PLMN_Search_Response:
                log_lte_plmn_search_response.decode(packet);
                break;
            case LOG_LTE_RRC_Log_Meas_Info:
                log_lte_rrc_log_meas_info.decode(packet);
                break;
            case LOG_LTE_RRC_Paging_UE:
                log_lte_rrc_paging_ue.decode(packet);
                break;
            default:
                Log.w("decode_rrc", "unhandled rrc msg type");
                break;
        }
    }

    static public void decode_nas(log_packet packet) {

        switch (log_code) {
            case LOG_LTE_NAS_ESM_Plain_OTA_Incoming_Msg:
                log_lte_nas_esm_plain_ota_incoming_msg.decode(packet);
                break;
            case LOG_LTE_NAS_ESM_Plain_OTA_Outgoing_Msg:
                log_lte_nas_esm_plain_ota_outgoing_msg.decode(packet);
                break;
            case LOG_LTE_NAS_EMM_Plain_OTA_Incoming_Msg:
                log_lte_nas_emm_plain_ota_incoming_msg.decode(packet);
                break;
            case LOG_LTE_NAS_EMM_Plain_OTA_Outgoing_Msg:
                log_lte_nas_emm_plain_ota_outcoming_msg.decode(packet);
                break;
            case LOG_LTE_NAS_EMM_State:
                log_lte_nas_emm_state.decode(packet);
                break;
            case LOG_LTE_NAS_EMM_Forbidden_TAI_List:
                log_lte_nas_emm_forbidden_tai_list.decode(packet);
                break;
            default:
                Log.w("decode_nas", "unhandled nas msg type");
                break;
        }
    }

    static public void decode_ml1(log_packet packet) {
        switch (log_code) {
            case LOG_LTE_Random_Access_Request_Report:
                msg1.decode(packet);
                break;
            case LOG_LTE_Random_Access_Response_Report:
                msg2.decode(packet);
                break;
            case LOG_LTE_UE_Identification_Message_Report:
                msg3.decode(packet);
                break;
            case LOG_LTE_Contention_Resolution_Message_Report:
                msg4.decode(packet);
                break;
            case LOG_LTE_Initial_Acquisition_Results:
                log_lte_initial_acquisition_results.decode(packet);
                break;
            case LOG_LTE_ML1_Connected_Mode_LTE_IntraFreq_Meas_Results:
                log_lte_ml1_connected_mode_lte_intrafreq_meas_results.decode(packet);
                break;
            case LOG_LTE_ML1_S_Criteria_Check_Procedure:
                log_lte_ml1_s_criteria_check_procedure.decode(packet);
                break;
            case LOG_LTE_ML1_Idle_Measurement_Request:
                log_lte_ml1_idle_measurement_request.decode(packet);
                break;
            case LOG_LTE_ML1_UE_Mobility_State_change:
                log_lte_ml1_ue_mobility_state_change.decode(packet);
                break;
            case LOG_LTE_ML1_Serving_Cell_Meas_and_Eval:
                log_lte_ml1_serving_cell_meas_and_eval.decode(packet);
                break;
            case LOG_LTE_ML1_Neighbor_Measurements:
                log_lte_ml1_neighbor_measurements.decode(packet);
                break;
            case LOG_LTE_ML1_Intra_Frequency_Cell_Reselection:
                log_lte_ml1_intra_frequency_cell_reselection.decode(packet);
                break;
            case LOG_LTE_ML1_Reselection_Candidates:
                log_lte_ml1_reselection_candidates.decode(packet);
                break;
            case LOG_LTE_ML1_Idle_Neighbor_Cell_Meas_Request_Response:
                log_lte_ml1_idle_neighbor_cell_meas_request_response.decode(packet);
                break;
            case LOG_LTE_ML1_Idle_Serving_Cell_Meas_Response:
                log_lte_ml1_idle_serving_cell_meas_response.decode(packet);
                break;
            case LOG_LTE_ML1_Connected_Neighbor_Meas_Request_Response:
                log_lte_ml1_connected_neighbor_cell_meas_request_response.decode(packet);
                break;
            case LOG_LTE_ML1_Serving_Cell_Information:
                log_lte_ml1_serving_cell_information.decode(packet);
                break;
            default:
                Log.w("decode_ml1", "unhandled ml1 msg type");
                break;
        }
    }
}
