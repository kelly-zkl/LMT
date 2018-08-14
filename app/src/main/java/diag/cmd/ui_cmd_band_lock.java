package diag.cmd;

import java.nio.ByteBuffer;

import diag.codec.binary_decoder;
import diag.ui_cmd_code;

import static diag.ui_cmd_rsp_code.rsp_code_ng;

/**
 * Created by xxx on 2018/2/8.
 */

public class ui_cmd_band_lock extends Object {

    public static int req_code = ui_cmd_code.cmd_code_lock_band;
    public static int rsp_code;
    public static long band_to_lock; // band 1~64
    public static long locked_band;

    public static byte[] to_bytes() {

        ByteBuffer buffer = ByteBuffer.allocate(12);
        buffer.putInt(req_code);
        buffer.putLong(band_to_lock);
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

        locked_band = decoder.getLongValue();

        System.out.printf("cmd_code: %d, nv_reg: %d, locked_band: %016x", cmd_code, nv_reg, locked_band);
        System.out.println();
    }
};
