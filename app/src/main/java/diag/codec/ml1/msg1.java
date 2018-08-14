package diag.codec.ml1;

import diag.codec.log_packet;
import diag.codec.log_packet_field;

import static diag.codec.log_packet_field.FIELD_TYPE_INT;
import static diag.codec.log_packet_field_dimension.TO_VOID;

/**
 * Created by xxx on 2018/2/7.
 */

public class msg1 {

    static log_packet_field[] field_fmt = {
            new log_packet_field(FIELD_TYPE_INT, "VERSION", 0, 1),
            new log_packet_field(FIELD_TYPE_INT, "Reserved", 1, 3),
            new log_packet_field(FIELD_TYPE_INT, "Preamble Sequence", 4, 4, 3, 6, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "Physical Root Index", 4, 4, 9, 10, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "Cyclic Shift", 4, 4, 19, 10, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "PRACH Tx Power", 8, 1),
            new log_packet_field(FIELD_TYPE_INT, "Beta PRACH", 9, 1),
            new log_packet_field(FIELD_TYPE_INT, "PRACH Frequency Offset", 10, 4, 3, 7, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "Preamble Format", 10, 4, 10, 3, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "Duplex Mode", 10, 4, 15, 1, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "Density Per 10 ms", 10, 4, 24, 3, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "PRACH Timing SFN", 14, 2, 0, 12, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "PRACH Timing Sub-fn", 14, 2, 12, 4, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "PRACH Window Start SFN", 16, 2, 0, 12, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "PRACH Window Start Sub-fn", 16, 2, 12, 4, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "PRACH Window End SFN", 18, 2, 0, 12, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "PRACH Window End Sub-fn", 18, 2, 12, 4, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "RA RNTI", 20, 2),
            new log_packet_field(FIELD_TYPE_INT, "PRACH Actual Tx Power", 24, 1),
    };

    static public void decode(log_packet packet) {
        System.out.println("msg1");
        packet.log_name = "msg1";
        packet.add_fields(field_fmt);

        if (packet.get_field("PRACH Tx Power").value_int > 112) {
            packet.get_field("PRACH Tx Power").value_int -= 256;
        }
    }
}
