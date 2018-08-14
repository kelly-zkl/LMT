package diag.cmd;

import java.nio.ByteBuffer;

import diag.binary_dumper;
import diag.codec.binary_decoder;

import static diag.ui_cmd_code.cmd_code_heartbeat;

/**
 * Created by xxx on 2018/3/20.
 */

public class ui_cmd_heartbeat {
    public static int req_code = cmd_code_heartbeat;
    public static int rsp_code;

    private static int DIAG_STATE_INIT = 0;
    private static int DIAG_STATE_WORKING = 1;
    private static int DIAG_STATE_GET_ROOT_FAIL = 2;
    private static int DIAG_STATE_COPYRIGHT_FAIL = 3;

    public static byte[] to_bytes() {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(req_code);
        return buffer.array();
    }

    private static int heartbeat_cnt = 0;
    private static int heartbeat_state = DIAG_STATE_INIT;

    public static boolean is_ok() {
        return heartbeat_cnt > 0 && heartbeat_state == DIAG_STATE_WORKING;
    }

    public static String get_state() {

        if (DIAG_STATE_INIT == heartbeat_state) {
            return "QCD 异常，请联系厂商解决。请重启手机尝试";
        }

        if (heartbeat_state == DIAG_STATE_GET_ROOT_FAIL) {
            return "打开设备失败，请查看Root权限管理。请重启手机尝试";
        }

        if (heartbeat_state == DIAG_STATE_COPYRIGHT_FAIL) {
            return "QCD 授权错误，请联系厂商解决";
        }

        return "QCD 正常";
    }

    public static void rsp_ind(byte[] bytes) {

        binary_decoder decoder = new binary_decoder();
        decoder.set_buff(bytes);

        req_code = decoder.getIntValue();
        rsp_code = decoder.getIntValue();
        int data_len = decoder.getIntValue();
        heartbeat_cnt = decoder.getIntValue();
        heartbeat_state = decoder.getIntValue();

        System.out.println("cmd_code_heartbeat. heartbeat_cnt: " + heartbeat_cnt + ", heartbeat_state: " + heartbeat_state);
    }
}
