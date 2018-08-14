package com.wisec.scanner.base;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.widget.Toast;

import com.wisec.scanner.bean.EventBean;
import com.wisec.scanner.utils.SystemManager;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import diag.cmd.ui_cmd_heartbeat;
import diag.codec.log_packet;
import diag.ui_cmd_req_send;
import diag.ui_cmd_rsp_recv;
import ui.ui_messager;

import static diag.ui_cmd_code.cmd_code_event_packet;
import static ui.ui_messager.ui_msg_diag_ind;
import static ui.ui_messager.ui_msg_heartbeat_err_ind;

public class BaseActivity extends AppCompatActivity {

    // Used to load the 'native-lib' library on application startup.
    static {
        System.loadLibrary("gpg-error");
        System.loadLibrary("gcrypt");
        System.loadLibrary("glib-2.0");
        System.loadLibrary("gmodule-2.0");
        System.loadLibrary("gthread-2.0");
        System.loadLibrary("wsutil");
        System.loadLibrary("wiretap");
        System.loadLibrary("dissector");
        System.loadLibrary("qcd");
        System.loadLibrary("native-lib");
        System.loadLibrary("ctrl");
        System.loadLibrary("reboot");

        SystemManager.init();
        SystemManager.start_qcd();
    }

    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler() {
        @Override
        public void handleMessage(android.os.Message msg) {
            if (ui_msg_heartbeat_err_ind == msg.what) {
                Toast.makeText(BaseActivity.this, ui_cmd_heartbeat.get_state(), Toast.LENGTH_LONG).show();
            }

            if (ui_msg_diag_ind == msg.what) {

                Bundle bundle = msg.getData();

                if (0 == bundle.size()) {
                    System.out.println("unhandled bundle...");
                    return;
                }

                ArrayList list = bundle.getParcelableArrayList("list");
                log_packet packet = (log_packet) list.get(0);

                String log_code = String.format("%04x", packet.log_code);

                EventBus.getDefault().post(packet);
            }

            if (cmd_code_event_packet == msg.what) {//事件记录消息101
                Bundle bundle = msg.getData();
                EventBean eventBean = (EventBean) bundle.getSerializable("event");
                EventBus.getDefault().post(eventBean);
            }
        }
    };

    private Runnable diagTask = new Runnable() {

        @Override
        public void run() {

            ui_cmd_rsp_recv ui_cmd_recv = new ui_cmd_rsp_recv("cmd_rsp_receiver");
            ui_cmd_recv.start();

            ui_cmd_req_send.init();

            try {

                for (; ; ) {
                    Thread.sleep(1000);
                    update_heartbeat_state();
                }

            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    };

//    private Thread thread;

    private int tick_cnt = 0;

    private void update_heartbeat_state() {
        tick_cnt++;
        if (tick_cnt < 5) {
            return;
        }

        if (ui_cmd_heartbeat.is_ok()) {
            return;
        }

        Message msg = new Message();
        msg.what = ui_msg_heartbeat_err_ind;
        handler.sendMessage(msg);

        tick_cnt = 0;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ui_messager.init(handler);
        new Thread(diagTask).start();
    }

//    @Override
//    protected void onResume() {
//        super.onResume();
//
//        if (null != thread) {
//            ui_messager.init(handler);
//            thread.start();
//        } else {
//            ui_messager.init(handler);
//            thread = new Thread(diagTask);
//            thread.start();
//        }
//    }

//    @Override
//    protected void onDestroy() {
//        super.onDestroy();
//        if (null != thread) {
//            thread = null;
//        }
//    }

    /**
     * A native method that is implemented by the 'native-lib' native library,
     * which is packaged with this application.
     */
    public native String stringFromJNI(byte[] asn1);

    public BaseActivity() {
        baseActivity = this;
    }

    public static BaseActivity getBaseActivity() {
        return baseActivity;
    }

    private static BaseActivity baseActivity;

    private long exitTime = 0;

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            exit();
            return false;
        }
        return super.onKeyDown(keyCode, event);
    }

    public void exit() {//2s之内返回2次，退出程序
        if ((System.currentTimeMillis() - exitTime) > 2000) {
            Toast.makeText(getApplicationContext(), "再按一次退出程序",
                    Toast.LENGTH_SHORT).show();
            exitTime = System.currentTimeMillis();
        } else {
            SystemManager.stop_process("libqcd");
            SystemManager.stop_process("libctrl");
            finish();
            System.exit(0);
        }
    }
}
