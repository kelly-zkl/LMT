package test;

import diag.cmd.ui_cmd_band_get;
import diag.cmd.ui_cmd_band_lock;

import static diag.ui_cmd_agent.get_supported_band;
import static diag.ui_cmd_agent.lock_band;
import static test.test_util.check_equal;
import static test.test_util.sleep;

public class band_test {

    private static void check_supported_band(long band) {
        System.out.printf("check_supported_band: expected: %016x, supported_band: %016x", band, ui_cmd_band_get.supported_band);
        System.out.println();
        check_equal(ui_cmd_band_get.supported_band, band);
    }

    private static void check_locked_band(long band) {
        System.out.printf("check_locked_band: expected: %016x, locked_band: %016x", band, ui_cmd_band_lock.locked_band);
        System.out.println();
        check_equal(ui_cmd_band_lock.locked_band, band);
    }

    public static void do_test() {

//        lock_band(0x000001E000000045L);
//        sleep();

        System.out.println(" ===== start band compatibility test =====");
        System.out.println(" 0. --- prepare test get ori band ---");

        get_supported_band();
        sleep();
        long ori_supported_band = ui_cmd_band_get.supported_band;

        System.out.println(" 1. --- lock supported band ---");
        lock_band(0x0000018000000000L);
        sleep();
        check_locked_band(0x0000018000000000L);

        System.out.println(" 2. --- restore ori band ---");
        lock_band(ori_supported_band);
        sleep();
        check_locked_band(ori_supported_band);

        System.out.println(" 3. --- get ori supported band ---");
        get_supported_band();
        sleep();
        check_supported_band(ori_supported_band);

        System.out.println(" ===== band compatibility test end =====");
    }
}