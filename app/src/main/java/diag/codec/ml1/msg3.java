package diag.codec.ml1;

import diag.codec.log_packet;
import diag.codec.log_packet_field;

import static diag.codec.log_packet_field.FIELD_TYPE_INT;
import static diag.codec.log_packet_field_dimension.TO_VOID;

/**
 * Created by xxx on 2018/2/7.
 */

public class msg3 {
    static log_packet_field[] field_fmt = {
            new log_packet_field(FIELD_TYPE_INT, "VERSION", 0, 1),
            new log_packet_field(FIELD_TYPE_INT, "Reserved", 1, 3),
            new log_packet_field(FIELD_TYPE_INT, "TPC", 4, 4, 3, 3, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "MCS", 4, 4, 6, 4, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "RIV", 4, 4, 10, 10, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "CQI", 4, 4, 20, 1, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "UL Delay", 4, 4, 21, 1, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "SFN", 4, 4, 22, 10, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "Sub-fn", 8, 4, 0, 4, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "Hopping Flag", 4, 4, 4, 1, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "Starting Resource Block", 8, 4, 5, 7, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "Num Resource Blocks", 8, 4, 12, 7, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "Transport Block Size Index", 8, 4, 19, 5, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "Modulation Type", 8, 4, 24, 2, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "Redundancy Version Index", 8, 4, 26, 2, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "HARQ ID", 8, 4, 28, 3, TO_VOID)
    };

    static public void decode(log_packet packet) {
        System.out.println("msg3");
        packet.log_name = "msg3";
        packet.add_fields(field_fmt);
    }
}
