package diag.cmd;

import java.nio.ByteBuffer;

import diag.codec.binary_decoder;
import diag.ui_cmd_code;

/**
 * Created by xxx on 2018/3/24.
 */

public class ui_cmd_set_pref_network {

    public static int NETWORK_MODE_GSM_ONLY       = 1; /* GSM only */
    public static int NETWORK_MODE_WCDMA_ONLY     = 2; /* WCDMA only */
    public static int NETWORK_MODE_CDMA_NO_EVDO   = 5; /* CDMA only */
    public static int NETWORK_MODE_EVDO_NO_CDMA   = 6; /* EvDo only */
    public static int NETWORK_MODE_LTE_ONLY       = 11; /* LTE Only mode. */
    public static int NETWORK_MODE_TD_SCDMA_ONLY  = 13; /* TD-SCDMA only */
    public static int NETWORK_MODE_DEFAULT_NETWORK = 100; /* restore 2 default preferred network */

    public static int req_code = ui_cmd_code.cmd_code_set_preferred_network;
    public static int rsp_code;

    public static byte[] to_bytes(int preferred_network) {

        ByteBuffer buffer = ByteBuffer.allocate(8);
        buffer.putInt(req_code);
        buffer.putInt(preferred_network);
        return buffer.array();
    }

    public static void rsp_ind(byte[] bytes) {

        binary_decoder decoder = new binary_decoder();
        decoder.set_buff(bytes);

        req_code = decoder.getIntValue();
        rsp_code = decoder.getIntValue();
        int data_len = decoder.getIntValue();
        int cmd_code = decoder.getByteValue();

        System.out.printf("cmd_code: %d", cmd_code);
        System.out.println();
    }
}
