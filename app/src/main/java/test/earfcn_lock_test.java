package test;

import diag.ui_cmd_agent;

import static diag.ui_cmd_agent.earfcn_unlock;

/**
 * Created by xxx on 2018/3/29.
 */

public class earfcn_lock_test {

    public static void do_test() {

        System.out.println(" ===== start earfcn lock test =====");
//        sleep();
//        earfcn_lock(38400);
//        sleep(10);
        ui_cmd_agent.earfcn_unlock();
        ui_cmd_agent.earfcn_lock(1825);
    }
}
