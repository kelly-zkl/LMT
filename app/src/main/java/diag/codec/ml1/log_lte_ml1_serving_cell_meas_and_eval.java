package diag.codec.ml1;

import diag.codec.log_packet;
import diag.codec.log_packet_field;

import static diag.codec.log_packet_field.FIELD_TYPE_INT;
import static diag.codec.log_packet_field_dimension.TO_RSRP;
import static diag.codec.log_packet_field_dimension.TO_RSRQ;
import static diag.codec.log_packet_field_dimension.TO_VOID;

/**
 * Created by xxx on 2018/2/7.0xB17F
 */

public class log_lte_ml1_serving_cell_meas_and_eval {

    static log_packet_field[] field_fmt = {
            new log_packet_field(FIELD_TYPE_INT, "VERSION", 0, 1),
            new log_packet_field(FIELD_TYPE_INT, "EARFCN", 4, 4),
            new log_packet_field(FIELD_TYPE_INT, "PCI", 8, 4, 0, 9, TO_VOID),
            new log_packet_field(FIELD_TYPE_INT, "RSRP", 12, 4, 0, 12, TO_RSRP),
            new log_packet_field(FIELD_TYPE_INT, "RSRQ", 20, 4, 0, 10, TO_RSRQ),
    };

    static public void decode(log_packet packet) {
        System.out.println("log_lte_ml1_serving_cell_meas_and_eval");
        packet.log_name = "log_lte_ml1_serving_cell_meas_and_eval";
        packet.add_fields(field_fmt);
    }
}

