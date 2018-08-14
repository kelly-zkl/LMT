package test;

import static diag.cmd.ui_cmd_mode_lpm.is_mode_lpm_ok;
import static diag.cmd.ui_cmd_mode_online.is_mode_online_ok;
import static diag.ui_cmd_agent.mode_lpm;
import static diag.ui_cmd_agent.mode_online;
import static test.test_util.sleep;

/**
 * Created by xxx on 2018/3/4.
 */

public class mode_test {

    public static void do_test() {

        System.out.println(" ===== start mode ctrl test =====");
        System.out.println(" 0. --- mode lpm test ---");
        mode_lpm();
        sleep();
        test_util.check(is_mode_lpm_ok());

        sleep(10000);

        System.out.println(" 1. --- mode online test ---");
        mode_online();
        sleep();
        test_util.check(is_mode_online_ok());
    }
}
