package diag.cmd;

import java.nio.ByteBuffer;

import diag.codec.binary_decoder;

import static diag.ui_cmd_code.cmd_code_get_support_bands;
import static diag.ui_cmd_rsp_code.rsp_code_ng;

/**
 * Created by xxx on 2018/1/27.
 */

public class ui_cmd_band_get extends Object {

    public static int req_code = cmd_code_get_support_bands;
    public static int rsp_code;
    public static long supported_band;

    public static byte[] to_bytes() {
        ByteBuffer buffer = ByteBuffer.allocate(4);
        buffer.putInt(req_code);
        return buffer.array();
    }

    public static void rsp_ind(byte[] bytes) {

        binary_decoder decoder = new binary_decoder();
        decoder.set_buff(bytes);

        req_code = decoder.getIntValue();
        rsp_code = decoder.getIntValue();
        int data_len = decoder.getIntValue();
        int cmd_code = decoder.getByteValue();
        int nv_reg = decoder.getShortValue();

        if (nv_reg != 6828) {
            rsp_code = rsp_code_ng;
            return;
        }

        supported_band = decoder.getLongValue();

        System.out.printf("cmd_code: %d, nv_reg: %d, supported_band: %016x", cmd_code, nv_reg, supported_band);
        System.out.println();
    }
};
