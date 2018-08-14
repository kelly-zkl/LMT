package diag.codec.ml1;

import diag.codec.log_packet;
import diag.codec.log_packet_field;

import static diag.codec.log_packet_field.FIELD_TYPE_INT;
import static diag.codec.log_packet_field_dimension.TO_VOID;

/**
 * Created by xxx on 2018/2/7.
 */

public class msg2 {
    static log_packet_field[] field_fmt = {
            new log_packet_field(FIELD_TYPE_INT, "VERSION", 0, 1),
            new log_packet_field(FIELD_TYPE_INT, "Reserved", 1, 3),
            new log_packet_field(FIELD_TYPE_INT, "RACH Procedure Type", 4, 4, 3, 1, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "RACH Procedure Mode", 4, 4, 4, 2, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "RNTI Type", 4, 4, 6, 4, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "RNTI Value", 4, 4, 10, 16, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "Timing Advance Included", 4, 4, 26, 1, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "SFN", 8, 2, 0, 12, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "Sub-fn", 8, 2, 12, 4, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "Timing Advance", 10, 2),
    };

    static public void decode(log_packet packet) {
        System.out.println("msg2");
        packet.log_name = "msg2";
        packet.add_fields(field_fmt);
    }
}
