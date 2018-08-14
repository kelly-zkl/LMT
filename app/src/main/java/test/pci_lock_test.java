package test;

import diag.ui_cmd_agent;

import static diag.ui_cmd_agent.pci_unlock;
import static test.test_util.sleep;

/**
 * Created by xxx on 2018/4/3.
 */

public class pci_lock_test {

    public static void do_test() {

        System.out.println(" ===== start pci lock test =====");
        sleep();
//        ui_cmd_agent.pci_lock(1650, 268);
//        test_util.check(is_mode_lpm_ok());
//        sleep(10);
        ui_cmd_agent.pci_unlock();
    }
}
