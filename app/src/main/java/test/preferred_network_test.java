package test;

import static diag.cmd.ui_cmd_set_pref_network.NETWORK_MODE_DEFAULT_NETWORK;
import static diag.cmd.ui_cmd_set_pref_network.NETWORK_MODE_GSM_ONLY;
import static diag.cmd.ui_cmd_set_pref_network.NETWORK_MODE_LTE_ONLY;
import static diag.cmd.ui_cmd_set_pref_network.NETWORK_MODE_WCDMA_ONLY;
import static diag.ui_cmd_agent.set_preferred_network;
import static test.test_util.sleep;

/**
 * Created by xxx on 2018/4/3.
 */

public class preferred_network_test {

    public static void do_test() {

        System.out.println(" ===== start preferred network test =====");
        sleep();
        set_preferred_network(NETWORK_MODE_GSM_ONLY);
        sleep(10);
        set_preferred_network(NETWORK_MODE_WCDMA_ONLY);
        sleep(10);
        set_preferred_network(NETWORK_MODE_LTE_ONLY);
        sleep(10);
        set_preferred_network(NETWORK_MODE_DEFAULT_NETWORK);
    }
}
