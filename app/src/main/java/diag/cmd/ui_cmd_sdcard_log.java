package diag.cmd;

import java.nio.ByteBuffer;

import diag.codec.binary_decoder;
import diag.ui_cmd_req_send;

import static diag.ui_cmd_code.cmd_code_log_2_sdcard_disable;
import static diag.ui_cmd_code.cmd_code_log_2_sdcard_enable;

/**
 * Created by xxx on 2018/3/3.
 */

public class ui_cmd_sdcard_log {

    public static String qualcomm_cfg_file;
    public static String log_file;

    public static void start_catch(String qualcomm_cfg_file, String log_file, int record_len) {
        ui_cmd_sdcard_log.qualcomm_cfg_file = qualcomm_cfg_file;
        ui_cmd_sdcard_log.log_file = log_file;
        System.out.println("ui_cmd_sdcard_log.start_catch. cfg: " + qualcomm_cfg_file + ", log: " + log_file);
        ui_cmd_req_send.send(satrt_req_to_bytes(record_len));
    }

    public static void stop_catch() {
        System.out.println("ui_cmd_sdcard_log.stop_req_to_bytes.");
        ui_cmd_req_send.send(stop_req_to_bytes());
    }

    private static byte[] satrt_req_to_bytes(int record_len) {
        int cfg_file_len = qualcomm_cfg_file.length();
        int log_file_len = log_file.length();
        ByteBuffer buffer = ByteBuffer.allocate(16 + cfg_file_len + log_file_len);
        buffer.putInt(cmd_code_log_2_sdcard_enable);
        buffer.putInt(record_len);
        buffer.putInt(cfg_file_len);
        buffer.put(qualcomm_cfg_file.getBytes());
        buffer.putInt(log_file_len);
        buffer.put(log_file.getBytes());
        return buffer.array();
    }

    private static byte[] stop_req_to_bytes() {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(cmd_code_log_2_sdcard_disable);
        return buffer.array();
    }

    public static void rsp_ind(byte[] bytes) {

        binary_decoder decoder = new binary_decoder();
        decoder.set_buff(bytes);

        int req_code = decoder.getIntValue();
        int rsp_code = decoder.getIntValue();
    }
}
