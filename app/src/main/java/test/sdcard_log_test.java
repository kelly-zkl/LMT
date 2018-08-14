package test;

import java.io.File;

import diag.cmd.ui_cmd_sdcard_log;
import diag.ui_cmd_agent;

import static test.test_util.check_large;
import static test.test_util.sleep;

/**
 * Created by xxx on 2018/3/6.
 */

public class sdcard_log_test {

    public static void do_test() {

        ui_cmd_agent.mode_lpm();
        System.out.println(" ===== start sdcard log test =====");
        ui_cmd_sdcard_log.start_catch("/data/attach.dmc", "/data/log.hdl", 3 * 1000);

        ui_cmd_agent.mode_online();
        sleep(10);
        ui_cmd_sdcard_log.stop_catch();

        File f = new File("/data/log.hdl");
        if (f.exists() && f.isFile()) {
            System.out.println("sdcard_log_test file exist. length: " + f.length());
            if (check_large(f.length(), 3 * 1000)) {
                System.out.println("sdcard_log_test success.");
                return;
            }
        }
        System.out.println("sdcard_log_test fail.");
    }
}
