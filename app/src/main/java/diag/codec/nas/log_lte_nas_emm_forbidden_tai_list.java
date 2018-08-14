package diag.codec.nas;

import diag.codec.log_packet;

/**
 * Created by xxx on 2018/2/7.
 */

public class log_lte_nas_emm_forbidden_tai_list {
    static public void decode(log_packet packet) {
        System.out.println("log_lte_nas_emm_forbidden_tai_list");
        packet.log_name = "log_lte_nas_emm_forbidden_tai_list";
    }
}
