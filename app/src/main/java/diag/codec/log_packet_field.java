package diag.codec;

import static diag.codec.log_packet_field_dimension.TO_VOID;

/**
 * Created by xxx on 2018/2/28.
 */

public class log_packet_field {


    public static int MAX_BYTESTREAM_LEN = 1024; // int. little endian

    public static int FIELD_TYPE_INT = 0; // int. little endian
    public static int FIELD_TYPE_DOUBLE = 1; // double. little endian
    public static int FIELD_TYPE_BYTE_STREAM = 2; // A stream of bytes. little endian

    public int type;
    public String name;
    public int start;
    public int len;
    public int start_bit;
    public int len_bit;
    public int value_int;
    public double value_double;
    public byte[] value_bytestream = new byte[MAX_BYTESTREAM_LEN];
    public int dimension;

    public log_packet_field(int type,
                            String name,
                            int start,
                            int len) {
        this.type = type;
        this.name = name;
        this.start = start;
        this.len = len;
        this.start_bit = 0;
        this.len_bit = 0;
        this.dimension = TO_VOID;
    }

    public log_packet_field(int type,
                            String name,
                            int start,
                            int len,
                            int start_bit,
                            int len_bit,
                            int dimension) {
        this.type = type;
        this.name = name;
        this.start = start;
        this.len = len;
        this.start_bit = start_bit;
        this.len_bit = len_bit;
        this.dimension = dimension;
    }

    public String toString() {

        String str = "\tname: " + name;

        if (FIELD_TYPE_INT == type) {
            str += ", value_int: " + value_int;
        } else if (FIELD_TYPE_DOUBLE == type) {
            str += ", value_double: " + value_double;
        } else if (FIELD_TYPE_BYTE_STREAM == type) {
            str += ", value_bytestream: ";

            for (int i = 0; i < len; i++) {
                str += String.format("0x%02x, ", value_bytestream[i]);
            }

            str += "\n";
        }

        str += " start: " + start + ", len: " + len;

        if (len_bit > 0) {
            str += ", start_bit: " + start_bit + ", len_bit: " + len_bit;
        }

        str += "\n";

        return str;
    }
}
