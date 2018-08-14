package diag.cmd;

import java.nio.ByteBuffer;

import diag.codec.binary_decoder;
import diag.ui_cmd_code;

import static com.wisec.scanner.utils.SystemManager.lock_earfcn;
import static com.wisec.scanner.utils.SystemManager.unlock_earfcn;
import static diag.ui_cmd_rsp_code.rsp_code_ok;

/**
 * Created by xxx on 2018/4/3.
 */

public class ui_cmd_earfcn_unlock {

    public static void earfcn_unlock() {
        unlock_earfcn();
    }
}
