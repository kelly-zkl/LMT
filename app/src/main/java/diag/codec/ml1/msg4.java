package diag.codec.ml1;

import diag.codec.log_packet;
import diag.codec.log_packet_field;

import static diag.codec.log_packet_field.FIELD_TYPE_INT;
import static diag.codec.log_packet_field_dimension.TO_VOID;

/**
 * Created by xxx on 2018/2/7.
 */

public class msg4 {
    static log_packet_field[] field_fmt = {
            new log_packet_field(FIELD_TYPE_INT, "VERSION", 0, 1),
            new log_packet_field(FIELD_TYPE_INT, "Reserved", 1, 3),
            new log_packet_field(FIELD_TYPE_INT, "SFN", 4, 4, 0, 10, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "Sub-fn", 4, 4, 10, 4, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "Contention Result", 4, 4, 14, 1, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "UL ACK Timing SFN", 4, 4, 15, 10, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "UL ACK Timing Sub-fn", 4, 4, 25, 4, TO_VOID)
    };

    static public void decode(log_packet packet) {
        System.out.println("msg4");
        packet.log_name = "msg4";
        packet.add_fields(field_fmt);
    }
}
