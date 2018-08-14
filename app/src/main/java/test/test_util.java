package test;

/**
 * Created by xxx on 2018/3/4.
 */

public class test_util {

    static int test_ok_cnt = 0;
    static int test_ng_cnt = 0;

    public static void sleep() {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void sleep(int time) {
        try {
            Thread.sleep(time);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void check(boolean result) {

        if (!result) {
            System.out.println("test check fail.");
            test_ng_cnt++;
            return;
        }
        System.out.println("test check ok.");
        test_ok_cnt++;
    }

    public static void check_equal(int val, int expected) {

        if (val != expected) {
            System.out.printf("fail. val: %08x, expected: %08x \n", val, expected);
            test_ng_cnt++;
            return;
        }
        test_ok_cnt++;
    }

    public static void check_equal(long val, long expected) {

        if (val != expected) {
            System.out.printf("fail. val: %016x, expected: %016x \n", val, expected);
            test_ng_cnt++;
            return;
        }
        test_ok_cnt++;
    }

    public static boolean check_equal(String s1, String s2) {

        if (!s1.equals(s2)) {
            test_ng_cnt++;
            return false;
        }
        test_ok_cnt++;
        return true;
    }

    public static boolean check_large(long val1, long val2) {
        if (val1 > val2) {
            test_ok_cnt++;
            return true;
        }
        test_ng_cnt++;
        return false;
    }

    private static void init() {
        test_ok_cnt = 0;
        test_ng_cnt = 0;
    }

    public static void test() {

        sleep(2000);

//        band_test.do_test();
//        System.out.println("test result. success: " + test_ok_cnt + " failed: " + test_ng_cnt);
//
//        init();
//        mode_test.do_test();
//        System.out.println("test result. success: " + test_ok_cnt + " failed: " + test_ng_cnt);
//
//        init();
//        asn1_decode_test.do_test();
//        System.out.println("test result. success: " + test_ok_cnt + " failed: " + test_ng_cnt);
//
//        init();
//        sdcard_log_test.do_test();
//        System.out.println("test result. success: " + test_ok_cnt + " failed: " + test_ng_cnt);

//        init();
//        earfcn_lock_test.do_test();
//        System.out.println("test result. success: " + test_ok_cnt + " failed: " + test_ng_cnt);

//        init();
//        pci_lock_test.do_test();
//        System.out.println("test result. success: " + test_ok_cnt + " failed: " + test_ng_cnt);

//        init();
//        preferred_network_test.do_test();
//        System.out.println("test result. success: " + test_ok_cnt + " failed: " + test_ng_cnt);
    }
}
