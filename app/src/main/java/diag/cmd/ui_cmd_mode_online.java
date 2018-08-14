package diag.cmd;

import java.nio.ByteBuffer;

import diag.codec.binary_decoder;
import diag.ui_cmd_code;

import static diag.ui_cmd_rsp_code.rsp_code_ng;
import static diag.ui_cmd_rsp_code.rsp_code_ok;

/**
 * Created by xxx on 2018/2/8.
 */

public class ui_cmd_mode_online {

    public static int req_code = ui_cmd_code.cmd_code_mode_online;
    public static int rsp_code = rsp_code_ng;

    public static boolean is_mode_online_ok() {
        return rsp_code == rsp_code_ok;
    }

    public static byte[] to_bytes() {
        rsp_code = rsp_code_ng;
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(req_code);
        return buffer.array();
    }

    public static void rsp_ind(byte[] bytes) {

        binary_decoder decoder = new binary_decoder();
        decoder.set_buff(bytes);

        req_code = decoder.getIntValue();
        rsp_code = decoder.getIntValue();

        System.out.println("online. req_code: " + req_code);
        System.out.println("online. rsp_code: " + rsp_code);
    }
}
