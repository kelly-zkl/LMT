package diag.codec.ml1;

import diag.codec.log_packet;

/**
 * Created by xxx on 2018/2/7.
 */

public class log_lte_ml1_connected_mode_lte_intrafreq_meas_results {
    static public void decode(log_packet packet) {
        System.out.println("log_lte_ml1_connected_mode_lte_intrafreq_meas_results");
        packet.log_name = "log_lte_ml1_connected_mode_lte_intrafreq_meas_results";
    }
}
