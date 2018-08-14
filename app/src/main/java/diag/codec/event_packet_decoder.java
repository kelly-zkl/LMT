package diag.codec;

import android.os.Bundle;
import android.os.Message;

import com.wisec.scanner.bean.EventBean;

import diag.binary_dumper;
import ui.ui_messager;

import static diag.ui_cmd_code.cmd_code_event_packet;

/**
 * Created by xxx on 2018/6/25.
 */

public class event_packet_decoder {

    static private binary_decoder decoder = new binary_decoder();

    static public void decode(byte[] cmd_rsp) {

        decoder.set_buff(cmd_rsp);

        int req_code = decoder.getIntValue();
        int rsp_code = decoder.getIntValue();
        int rsp_len = decoder.getIntValue();

        System.out.println("---- event packet " + " len: " + rsp_len + " ----");
        System.out.println("req_code: " + req_code + " rsp_ok: " + rsp_code);
        binary_dumper.dump(cmd_rsp, rsp_len);

        // 小区更新原因
        int cause = decoder.getByteValue();
        int earfcn = decoder.getShortValue();
        int pci = decoder.getShortValue();

        earfcn &= 0xffff;

        EventBean eventBean = new EventBean(pci, earfcn, cause);

        System.out.println("cause: " + cause + " earfcn: " + earfcn + " pci: " + pci);

        notity_ui(eventBean);
    }

    static private void notity_ui(EventBean eventBean) {

        Message msg = new Message();
        Bundle bundle = new Bundle();

        bundle.putSerializable("event", eventBean);
        msg.what = cmd_code_event_packet; // update ui
        msg.arg1 = cmd_code_event_packet;
        msg.setData(bundle);
        ui_messager.notify(msg);
    }
}
