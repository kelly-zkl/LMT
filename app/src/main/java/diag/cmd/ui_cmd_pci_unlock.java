package diag.cmd;

import java.nio.ByteBuffer;

import diag.codec.binary_decoder;
import diag.ui_cmd_code;

import static com.wisec.scanner.utils.SystemManager.lock_pci;
import static com.wisec.scanner.utils.SystemManager.unlock_pci;

/**
 * Created by xxx on 2018/4/3.
 */

public class ui_cmd_pci_unlock {
    public static void pci_unlock() {
        unlock_pci();
    }
}
