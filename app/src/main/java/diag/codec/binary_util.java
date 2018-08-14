package diag.codec;

/**
 * Created by xxx on 2018/3/1.
 */

public class binary_util {

    public static short to_big_endian(short value) {
        short tmp = (short) ((value & 0xff) << 8);
        tmp += (value >>> 8 & 0xff);
        return tmp;
    }

    public static int to_big_endian(int value) {
        int tmp = (value & 0xff) << 24;
        tmp += (value >>> 8 & 0xff) << 16;
        tmp += (value >>> 16 & 0xff) << 8;
        tmp += (value >>> 24 & 0xff);
        return tmp;
    }

    public static int get_bit_le(int value, int start, int len) {
        int tmp = (value >> start);
        int right = tmp << (32 - len);
        int left = right >>> (32 - len);
        return left;
    }
}
