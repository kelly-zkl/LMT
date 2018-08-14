package diag;

import diag.cmd.ui_cmd_asn1_decode;
import diag.cmd.ui_cmd_band_get;
import diag.cmd.ui_cmd_band_lock;
import diag.cmd.ui_cmd_earfcn_lock;
import diag.cmd.ui_cmd_earfcn_unlock;
import diag.cmd.ui_cmd_heartbeat;
import diag.cmd.ui_cmd_mode_lpm;
import diag.cmd.ui_cmd_mode_online;
import diag.cmd.ui_cmd_pci_lock;
import diag.cmd.ui_cmd_pci_unlock;
import diag.cmd.ui_cmd_sdcard_log;
import diag.cmd.ui_cmd_set_pref_network;
import diag.codec.event_packet_decoder;
import diag.codec.log_packet_decoder;

import static diag.ui_cmd_code.cmd_code_ans1_decode;
import static diag.ui_cmd_code.cmd_code_earfcn_lock;
import static diag.ui_cmd_code.cmd_code_earfcn_unlock;
import static diag.ui_cmd_code.cmd_code_event_packet;
import static diag.ui_cmd_code.cmd_code_get_support_bands;
import static diag.ui_cmd_code.cmd_code_heartbeat;
import static diag.ui_cmd_code.cmd_code_lock_band;
import static diag.ui_cmd_code.cmd_code_log_2_sdcard_disable;
import static diag.ui_cmd_code.cmd_code_log_2_sdcard_enable;
import static diag.ui_cmd_code.cmd_code_log_packet;
import static diag.ui_cmd_code.cmd_code_mode_lpm;
import static diag.ui_cmd_code.cmd_code_mode_online;
import static diag.ui_cmd_code.cmd_code_pci_lock;
import static diag.ui_cmd_code.cmd_code_pci_unlock;
import static diag.ui_cmd_code.cmd_code_set_preferred_network;

/**
 * Created by xxx on 2018/1/27.
 */

public class ui_cmd_agent {

    public static void send_heart_beat() {
        System.out.println("send_heart_beat. req_code: " + ui_cmd_heartbeat.req_code);
        ui_cmd_req_send.send(ui_cmd_heartbeat.to_bytes());
    }

    public static void get_supported_band() {
        System.out.println("get_supported_band. req_code: " + ui_cmd_band_get.req_code);
        ui_cmd_req_send.send(ui_cmd_band_get.to_bytes());
    }

    public static void lock_band(long band) {
        ui_cmd_band_lock.band_to_lock = band;
        System.out.printf("lock_band. cmd_code: %d, band_to_lock: %016x", ui_cmd_band_lock.req_code, ui_cmd_band_lock.band_to_lock);
        System.out.println();
        ui_cmd_req_send.send(ui_cmd_band_lock.to_bytes());
    }

    public static void mode_online() {
        System.out.println("mode_online. req_code: " + ui_cmd_mode_online.req_code);
        ui_cmd_req_send.send(ui_cmd_mode_online.to_bytes());
    }

    public static void mode_lpm() {
        System.out.println("mode_lpm. req_code: " + ui_cmd_mode_lpm.req_code);
        ui_cmd_req_send.send(ui_cmd_mode_lpm.to_bytes());
    }

    public static void earfcn_lock(int earfcn) {
        System.out.println("earfcn_lock. earfcn_2_lock: " + earfcn);
        ui_cmd_earfcn_lock.earfcn_lock(earfcn);
    }

    public static void earfcn_unlock() {
        System.out.println("earfcn_unlock.");
        ui_cmd_earfcn_unlock.earfcn_unlock();
    }

    public static void pci_lock(int earfcn, int pci) {
        System.out.println("earfcn_lock. earfcn_2_lock: " + earfcn + " pci: " + pci);
        ui_cmd_pci_lock.pci_lock(earfcn, pci);
    }

    public static void pci_unlock() {
        System.out.println("pci_unlock.");
        ui_cmd_pci_unlock.pci_unlock();
    }

    public static void set_preferred_network(int network) {
        System.out.println("set_preferred_network. req_code: " + ui_cmd_set_pref_network.req_code + " preferred_network: " + network);
        ui_cmd_req_send.send(ui_cmd_set_pref_network.to_bytes(network));
    }

//    public static void ans1_decode_req(int signal_type, byte[] bytes, int signal_len) {
//        ui_cmd_asn1_decode.decode_req(signal_type, bytes, signal_len);
//    }

    public static void start_sd_log(String qualcomm_dmc, String file_name, int record_len) {
        ui_cmd_sdcard_log.start_catch(qualcomm_dmc, file_name, record_len);
    }

    public static void stop_sd_log() {
        ui_cmd_sdcard_log.stop_catch();
    }

    public static void cmd_rsp_ind(byte[] cmd_rsp) {

        int cmd_code = cmd_rsp[0];

        System.out.println("cmd_rsp_ind. cmd_code: " + cmd_code);

        switch (cmd_code) {
            case cmd_code_log_packet:
                log_packet_decoder.decode(cmd_rsp);
                break;
            case cmd_code_event_packet:
                event_packet_decoder.decode(cmd_rsp);
                break;
            case cmd_code_heartbeat:
                ui_cmd_heartbeat.rsp_ind(cmd_rsp);
                break;
            case cmd_code_get_support_bands:
                ui_cmd_band_get.rsp_ind(cmd_rsp);
                break;
            case cmd_code_lock_band:
                ui_cmd_band_lock.rsp_ind(cmd_rsp);
                break;
            case cmd_code_ans1_decode:
                ui_cmd_asn1_decode.result_ind(cmd_rsp);
                break;
            case cmd_code_mode_lpm:
                ui_cmd_mode_lpm.rsp_ind(cmd_rsp);
                break;
            case cmd_code_mode_online:
                ui_cmd_mode_online.rsp_ind(cmd_rsp);
                break;
            case cmd_code_log_2_sdcard_enable:
            case cmd_code_log_2_sdcard_disable:
                ui_cmd_sdcard_log.rsp_ind(cmd_rsp);
                break;
            case cmd_code_earfcn_lock:
                break;
            case cmd_code_earfcn_unlock:
                break;
            case cmd_code_pci_lock:
                break;
            case cmd_code_pci_unlock:
                break;
            case cmd_code_set_preferred_network:
                ui_cmd_set_pref_network.rsp_ind(cmd_rsp);
            default:
                System.out.println("unknown cmd rsp msg!!!");
                break;
        }
    }
}

