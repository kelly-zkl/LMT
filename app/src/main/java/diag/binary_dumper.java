package diag;

/**
 * Created by xxx on 2018/2/9.
 */

public class binary_dumper {

    static public void dump(byte buff[], int len) {

        for (int i = 0; i < len; ++i) {
            if ((i > 0) && (0 == (i % 16))) {
                System.out.println();
            }
            System.out.printf("%02x ", buff[i]);
        }

        System.out.println();
    }
}
