package ui;

import android.os.Handler;
import android.os.Message;

//import static diag.ui_cmd_agent.ans1_decode_req;

/**
 * Created by xxx on 2018/2/8.
 */

public class ui_messager {

    public static final int ui_msg_diag_ind = 0;
    public static final int ui_msg_asn1_decoded_ind = 1;
    public static final int ui_msg_heartbeat_err_ind = 2;

    static private Handler handler = null;

    static public void init(Handler h) {
        handler = h;
    }

    static public void notify(Message msg) {
        handler.sendMessage(msg);
    }

//    static byte[] req_bytes;
//    static int req_signal_type;
//    static int req_signal_len;

//    static public void qcd_decode_request(int signal_type, byte[] bytes, int len) {
//
//        req_bytes = bytes;
//        req_signal_type = signal_type;
//        req_signal_len = len;
//
//        System.out.println("req_signal_type: " + req_signal_type);
//        System.out.println("req_signal_len: " + req_signal_len);
//
//        Runnable qcd_req_task = new Runnable() {
//            public void run() {
////            ans1_decode_req(req_signal_type, req_bytes, req_signal_len);
//            }
//        };
//
//        new Thread(qcd_req_task).start();
//    }
}
