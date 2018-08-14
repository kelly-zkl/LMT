package diag.codec;

/**
 * Created by xxx on 2018/2/7.
 */

public class log_packet_code {

    // rrc
    public static final int LOG_LTE_RRC_OTA = 0xB0C0;  // LTE OTA message (0xB0C0)
    public static final int LOG_LTE_RRC_MIB = 0xB0C1;  // LTE RRC MIB Message Log Packet (0xB0C1)
    public static final int LOG_LTE_RRC_Serving_Cell_Info = 0xB0C2;  // LTE RRC Serving Cell Info Log Pkt (0xB0C2)
    public static final int LOG_LTE_PLMN_Search_Request = 0xB0C3;  // LTE PLMN Search Request (0xB0C3)
    public static final int LOG_LTE_PLMN_Search_Response = 0xB0C4;  // LTE PLMN Search Response (0xB0C4)
    public static final int LOG_LTE_RRC_Log_Meas_Info = 0xB0CA; // LTE RRC Log Meas Info (0xB0CA)
    public static final int LOG_LTE_RRC_Paging_UE = 0xB0CB; // LTE RRC Paging UE (0xB0CB)
    public static final int LOG_LTE_RRC_MAX = 0xB0D0;

    // nas
    public static final int LOG_LTE_NAS_ESM_Plain_OTA_Incoming_Msg = 0xB0E2; // LTE NAS ESM Plain OTA Incoming Msg (0xB0E2)
    public static final int LOG_LTE_NAS_ESM_Plain_OTA_Outgoing_Msg = 0xB0E3; //LTE NAS ESM Plain OTA Outgoing Msg (0xB0E3)
    public static final int LOG_LTE_NAS_EMM_Plain_OTA_Incoming_Msg = 0xB0EC; //LTE NAS EMM Plain OTA Incoming Msg (0xB0EC)
    public static final int LOG_LTE_NAS_EMM_Plain_OTA_Outgoing_Msg = 0xB0ED; //LTE NAS EMM Plain OTA Outgoing Msg (0xB0ED)
    public static final int LOG_LTE_NAS_EMM_State = 0xB0EE; //LTE NAS EMM State (0xB0EE)
    public static final int LOG_LTE_NAS_EMM_Forbidden_TAI_List = 0xB0F6; //LTE NAS EMM Forbidden TAI List (0xB0F6)
    public static final int LOG_LTE_NAS_MAX = 0xB0FF;

    // ml1
    public static final int LOG_LTE_Random_Access_Request_Report = 0xB167; // LTE Random Access Request (MSG1) Report (0xB167)
    public static final int LOG_LTE_Random_Access_Response_Report = 0xB168; //LTE Random Access Response (MSG2) Report (0xB168)
    public static final int LOG_LTE_UE_Identification_Message_Report = 0xB169; //LTE UE Identification Message (MSG3) Report (0xB169)
    public static final int LOG_LTE_Contention_Resolution_Message_Report = 0xB16A; //LTE Contention Resolution Message (MSG4) Report (0xB16A)
    public static final int LOG_LTE_Initial_Acquisition_Results = 0xB176; //LTE Initial Acquisition Results (0xB176)
    public static final int LOG_LTE_ML1_Connected_Mode_LTE_IntraFreq_Meas_Results = 0xB179; //LTE ML1 Connected Mode LTE Intra-Freq Meas Results (0xB179)
    public static final int LOG_LTE_ML1_S_Criteria_Check_Procedure = 0xB17A; // LTE ML1 S Criteria Check Procedure (0xB17A);
    public static final int LOG_LTE_ML1_Idle_Measurement_Request = 0xB17D; // LTE ML1 Idle Measurement Request (0xB17D)
    public static final int LOG_LTE_ML1_UE_Mobility_State_change = 0xB17E; // LTE ML1 UE Mobility State change (0xB17E)
    public static final int LOG_LTE_ML1_Serving_Cell_Meas_and_Eval = 0xB17F; //LTE ML1 Serving Cell Meas and Eval (0xB17F)
    public static final int LOG_LTE_ML1_Neighbor_Measurements = 0xB180; //LTE ML1 Neighbor Measurements (0xB180)
    public static final int LOG_LTE_ML1_Intra_Frequency_Cell_Reselection = 0xB181; //LTE ML1 Intra Frequency Cell Reselection (0xB181)
    public static final int LOG_LTE_ML1_Reselection_Candidates = 0xB186; // LTE ML1 Reselection Candidates (0xB186)
    public static final int LOG_LTE_ML1_Idle_Neighbor_Cell_Meas_Request_Response = 0xB192; // LTE ML1 Idle Neighbor Cell Meas Request/Response(0xB192)
    public static final int LOG_LTE_ML1_Idle_Serving_Cell_Meas_Response = 0xB193; //
    public static final int LOG_LTE_ML1_Connected_Neighbor_Meas_Request_Response = 0xB195; // LTE ML1 Connected Neighbor Meas Request/Response (0xB195)
    public static final int LOG_LTE_ML1_Serving_Cell_Information = 0xB197; //LTE ML1 Serving Cell Information (0xB197)
}
