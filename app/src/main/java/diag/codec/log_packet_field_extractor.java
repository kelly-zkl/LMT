package diag.codec;

import static diag.codec.log_packet_decoder.is_debug_on;
import static diag.codec.log_packet_field_dimension.TO_VOID;

/**
 * Created by xxx on 2018/2/28.
 */

public class log_packet_field_extractor {

    static void extract_int(binary_decoder decoder, log_packet_field field) {

        int value_byte = 0;
        switch (field.len) {
            case 1:
                value_byte = decoder.getByteValue(field.start) & 0xff;
                break;
            case 2:
                value_byte = decoder.getShortValue(field.start) & 0xffff;
                break;
            case 4:
                value_byte = decoder.getIntValue(field.start);
                break;
        }

        int value_bit = value_byte;
        if (field.len_bit != 0) {
            value_bit = binary_util.get_bit_le(value_byte, field.start_bit, field.len_bit);
        }

        int value_converted = value_bit;
        if (field.dimension != TO_VOID) {
            value_converted = log_packet_field_dimension.convert(field.dimension, value_bit);
        }

        field.value_int = value_converted;

        if (is_debug_on) {
            System.out.printf("type: %d, value_byte: 0x%08x, value_bit: 0x%08x", field.type, value_byte, value_bit);
            if (field.dimension != TO_VOID) {
                System.out.println(", dimension: " + field.dimension + ", converted: " + field.value_int);
            } else {
                System.out.println();
            }
        }
    }

    static void extract_double(binary_decoder decoder, log_packet_field field) {

        int value_byte = 0;
        switch (field.len) {
            case 1:
                value_byte = decoder.getByteValue(field.start);
                break;
            case 2:
                value_byte = decoder.getShortValue(field.start) & 0xffff;
                break;
            case 4:
                value_byte = decoder.getIntValue(field.start);
                break;
        }

        int value_bit = value_byte;
        if (field.len_bit != 0) {
            value_bit = binary_util.get_bit_le(value_byte, field.start_bit, field.len_bit);
        }

        double value_converted = value_bit;
        if (field.dimension != TO_VOID) {
            value_converted = log_packet_field_dimension.convert(field.dimension, value_bit);
        }

        field.value_double = value_converted;

        if (is_debug_on) {
            System.out.printf("type: %d, value_double: %f, value_bit: 0x%08x",
                    field.type, field.value_double, value_bit);
            if (field.dimension != TO_VOID) {
                System.out.println(", dimension: " + field.dimension + ", converted: " + field.value_double);
            } else {
                System.out.println();
            }
        }
    }

    static void extract_bytestream(binary_decoder decoder, log_packet_field field) {
        System.out.printf("type: %d\n", field.type);
        decoder.get_bytestream(field.value_bytestream, field.start, field.len);
    }
}
