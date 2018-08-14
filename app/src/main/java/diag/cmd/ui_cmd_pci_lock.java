package diag.cmd;

import java.nio.ByteBuffer;

import diag.codec.binary_decoder;
import diag.ui_cmd_code;

import static com.wisec.scanner.utils.SystemManager.lock_earfcn;
import static com.wisec.scanner.utils.SystemManager.lock_pci;

/**
 * Created by xxx on 2018/4/3.
 */

public class ui_cmd_pci_lock {

    public static void pci_lock(int earfcn_2_lock, int pci_2_lock) {
        lock_pci(earfcn_2_lock, pci_2_lock);
    }
}
