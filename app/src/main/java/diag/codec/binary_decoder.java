package diag.codec;

import static diag.codec.log_packet_field.MAX_BYTESTREAM_LEN;

/**
 * Created by xxx on 2018/2/7.
 */

public class binary_decoder {

    private byte[] b;
    private int read_byte_idx;
    private int read_bit_idx;

    public void set_buff(byte[] buff) {
        b = buff;
        read_byte_idx = 0;
        read_bit_idx = 0;
    }

    public void get_bytestream(byte[] bytestream, int start, int len) {

        if (len >= MAX_BYTESTREAM_LEN) {
            System.out.println("len: " + len + ", MAX_BYTESTREAM_LEN: " + MAX_BYTESTREAM_LEN);
            return;
        }

        for (int i = 0; i < len; i++) {
            bytestream[i] = b[read_byte_idx + start + i];
        }
    }

    private int get_bit(int byte_idx, int bit_idx, int num) {

        if (num + bit_idx <= 8) {
            return ((b[byte_idx] << bit_idx) >>> (8 - num)) & 0xff;
        }

        if (num + bit_idx <= 16) {
            int val = ((b[byte_idx] << 8) & 0x0000ff00) + (b[byte_idx + 1] & 0xff);
            val = (val << bit_idx) & 0xffff;
            val = (val >> (16 - num)) & 0xffff;
            return val;
        }

        if (num + bit_idx <= 24) {
            int val = ((b[byte_idx] << 16) & 0x00ff0000) + ((b[byte_idx + 1] << 8) & 0x0000ff00) + (b[byte_idx + 2] & 0xff);
            val = (val << bit_idx) & 0xffffff;
            val = (val >>> (24 - num)) & 0xffffff;
            return val;
        }

        if (num + bit_idx <= 32) {
            int val = ((b[byte_idx] << 24) & 0xff000000) + ((b[byte_idx + 1] << 16) & 0x00ff0000) + ((b[byte_idx + 2] << 8) & 0x0000ff00) + (b[byte_idx + 3] & 0xff);
            val = (val << bit_idx) & 0xffffffff;
            val = (val >>> (32 - num)) & 0xffffffff;
            return val;
        }

        if (num + bit_idx <= 40) {
            long val = ((b[byte_idx] << 32) & 0xff00000000l) + ((b[byte_idx + 1] << 24) & 0xff000000) + ((b[byte_idx + 2] << 16) & 0x00ff0000) + ((b[byte_idx + 3] << 8) & 0x0000ff00) + (b[byte_idx + 4] & 0xff);
            val = (val << bit_idx) & 0xffffffffffl;
            val = (val >>> (40 - num)) & 0xffffffffffl;
            return (int) (val & 0xffffffffffl);
        }

        System.out.println("bit decode failed");

        return -1;
    }

    private int get_bit_little_endian(int byte_idx, int bit_idx, int num) {

        if (num + bit_idx <= 8) {
            return ((b[byte_idx] << bit_idx) >>> (8 - num)) & 0xff;
        }

        if (num + bit_idx <= 16) {
            int val = ((b[byte_idx + 1] << 8) & 0x0000ff00) + (b[byte_idx] & 0xff);
            val = (val << bit_idx) & 0xffff;
            val = (val >>> (16 - num)) & 0xffff;
            return val;
        }

        if (num + bit_idx <= 24) {
            int val = ((b[byte_idx + 2] << 16) & 0x00ff0000) + ((b[byte_idx + 1] << 8) & 0x0000ff00) + (b[byte_idx] & 0xff);
            val = (val << bit_idx) & 0xffffff;
            val = (val >>> (24 - num)) & 0xffffff;
            return val;
        }

        if (num + bit_idx <= 32) {
            int val = ((b[byte_idx + 3] << 24) & 0xff000000) + ((b[byte_idx + 2] << 16) & 0x00ff0000) + ((b[byte_idx + 1] << 8) & 0x0000ff00) + (b[byte_idx + 0] & 0xff);
            val = (val << bit_idx) & 0xffffffff;
            val = (val >>> (32 - num)) & 0xffffffff;
            return val;
        }

        if (num + bit_idx <= 40) {
            long val = ((b[byte_idx + 4] << 32) & 0xff00000000l) + ((b[byte_idx + 3] << 24) & 0xff000000) + ((b[byte_idx + 2] << 16) & 0x00ff0000) + ((b[byte_idx + 1] << 8) & 0x0000ff00) + (b[byte_idx + 0] & 0xff);
            val = (val << bit_idx) & 0xffffffffffl;
            val = (val >> (40 - num)) & 0xffffffffffl;
            return (int) (val & 0xffffffffffl);
        }

        System.out.println("bit decode failed");

        return -1;
    }

    public int getBitValue(int start, int num) {
        int byte_idx = (read_bit_idx + start) / 8;
        int bit_idx = (read_bit_idx + start) % 8;
        return get_bit_little_endian(byte_idx, bit_idx, num);
    }

    private int getBitValue(int num) {

        int byte_idx = read_bit_idx / 8;
        int bit_idx = read_bit_idx % 8;

        read_bit_idx += num;
        read_byte_idx = read_bit_idx / 8;

        return get_bit(byte_idx, bit_idx, num);
    }

    public byte getByteValue() {

        byte val = b[read_byte_idx];
        read_byte_idx++;
        read_bit_idx += 8;

        return (byte) (val & 0xff);
    }

    public byte getByteValue(int start) {
        start += read_byte_idx;
        byte val = b[start];
        return (byte) (val & 0xff);
    }

    public short getShortValue(int start) {
        start += read_byte_idx;
        short val = (short) (b[start] & 0xff);
        val = (short) (val + (short) ((b[start + 1] << 8) & 0xff00));
        return (short) (val & 0xffff);
    }

    public short getShortValue() {

        short val = (short) (b[read_byte_idx] & 0xff);
        val = (short) (val + (short) ((b[read_byte_idx + 1] << 8) & 0xff00));
        read_byte_idx += 2;
        read_bit_idx += 16;

        return (short) (val & 0xffff);
    }

    public short getShortValueBigEdian() {

        short val = (short) (b[read_byte_idx + 1] & 0xff);
        val = (short) (val + (short) ((b[read_byte_idx] << 8) & 0xff00));
        read_byte_idx += 2;
        read_bit_idx += 16;

        return (short) (val & 0xffff);
    }

    public int getIntValue() {

        int val = b[read_byte_idx] & 0xff;
        val = val + (b[read_byte_idx + 1] << 8 & 0x0000ff00);
        val = val + (b[read_byte_idx + 2] << 16 & 0x00ff0000);
        val = val + (b[read_byte_idx + 3] << 24 & 0xff000000);

        read_byte_idx += 4;
        read_bit_idx += 32;

        return val;
    }

    public int getIntValue(int start) {
        start += read_byte_idx;
        int val = b[start] & 0xff;
        val = val + (b[start + 1] << 8 & 0x0000ff00);
        val = val + (b[start + 2] << 16 & 0x00ff0000);
        val = val + (b[start + 3] << 24 & 0xff000000);
        return val;
    }

    public int getIntValueBigEndian() {

        int val = b[read_byte_idx + 3] & 0xff;
        val = val + (b[read_byte_idx + 2] << 8 & 0x0000ff00);
        val = val + (b[read_byte_idx + 1] << 16 & 0x00ff0000);
        val = val + (b[read_byte_idx + 0] << 24 & 0xff000000);

        read_byte_idx += 4;
        read_bit_idx += 32;

        return val;
    }

    public long getLongValue() {

        long val_low = b[read_byte_idx] << 24 & 0xff000000;
        val_low = val_low + (b[read_byte_idx + 1] << 16 & 0x00ff0000);
        val_low = val_low + (b[read_byte_idx + 2] << 8 & 0x0000ff00);
        val_low = val_low + (b[read_byte_idx + 3] << 0 & 0xff);
        read_byte_idx += 4;
        read_bit_idx += 32;

        long val_high = b[read_byte_idx] << 24 & 0xff000000;
        val_high = val_high + (b[read_byte_idx + 1] << 16 & 0x00ff0000);
        val_high = val_high + (b[read_byte_idx + 2] << 8 & 0x0000ff00);
        val_high = val_high + (b[read_byte_idx + 3] << 0 & 0xff);
        read_byte_idx += 4;
        read_bit_idx += 32;

        return (val_low << 32) | ((val_high << 32) >>> 32);
    }
}
