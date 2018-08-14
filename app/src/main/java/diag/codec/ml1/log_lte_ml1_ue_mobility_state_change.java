package diag.codec.ml1;

import diag.codec.log_packet;

/**
 * Created by xxx on 2018/2/18.
 */

public class log_lte_ml1_ue_mobility_state_change {
    static public void decode(log_packet packet) {
        System.out.println("log_lte_ml1_ue_mobility_state_change");
        packet.log_name = "log_lte_ml1_ue_mobility_state_change";
    }
}
