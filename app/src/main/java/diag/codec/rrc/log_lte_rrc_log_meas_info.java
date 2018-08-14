package diag.codec.rrc;

import diag.codec.log_packet;

/**
 * Created by xxx on 2018/2/7.
 */

public class log_lte_rrc_log_meas_info {
    static public void decode(log_packet packet) {
        System.out.println("log_lte_rrc_log_meas_info");
        packet.log_name = "log_lte_rrc_log_meas_info";
    }
}
