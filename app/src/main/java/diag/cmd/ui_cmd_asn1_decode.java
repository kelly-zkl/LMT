package diag.cmd;

import android.os.Bundle;
import android.os.Message;

import java.nio.ByteBuffer;
import java.util.ArrayList;

import diag.binary_dumper;
import diag.codec.binary_decoder;
import diag.ui_cmd_req_send;

import static diag.ui_cmd_code.cmd_code_ans1_decode;
import static diag.ui_cmd_rsp_code.rsp_code_ng;
import static diag.ui_cmd_rsp_code.rsp_code_ok;
import static ui.ui_messager.ui_msg_asn1_decoded_ind;

/**
 * Created by xxx on 2018/3/3.
 */

public class ui_cmd_asn1_decode {

    public static int req_code = cmd_code_ans1_decode;
    public static int rsp_code = rsp_code_ng;
    private static byte[] req_bytes;

    public static boolean is_asn1_decode_ok() {
        return rsp_code == rsp_code_ok;
    }

    public static void decode_req(int signal_type, byte[] bytes, int signal_len) {
        req_bytes = ui_cmd_asn1_decode.to_bytes(signal_type, bytes, signal_len);
        System.out.println("ans1_decode_req. req_code: " + req_code + ", length: " + bytes.length);
        binary_dumper.dump(req_bytes, req_bytes.length);
        ui_cmd_req_send.send(req_bytes);
    }

    private static byte[] to_bytes(int signal_type, byte[] bytes, int signal_len) {

        ByteBuffer buffer = ByteBuffer.allocate(signal_len + 12);

        buffer.putInt(req_code);
        buffer.putInt(signal_type);
        buffer.putInt(signal_len);
        buffer.put(bytes, 0, signal_len);

        return buffer.array();
    }

    static private final int max_xml_len = 100 * 1024;
    static public byte[] decoded_asn1_in_xml = new byte[max_xml_len];
    static public int byte_num = 0;

    public static void result_ind(byte[] bytes) {

        binary_decoder decoder = new binary_decoder();
        decoder.set_buff(bytes);
        req_code = decoder.getIntValue();
        rsp_code = decoder.getIntValue();
        int segment_id = decoder.getIntValue();
        int data_len = decoder.getIntValue();

        if (0 == segment_id) {
            byte_num = 0;
        }

        System.out.println("byte_num: " + byte_num + ", data_len: " + data_len
                + ", max_xml_len: " + max_xml_len + ", segment_id: " + segment_id);

        if (byte_num + data_len >= max_xml_len) {
            return;
        }

        for (int i = 0; i < data_len; i++) {
            decoded_asn1_in_xml[byte_num++] = bytes[16 + i];
        }

        if (data_len < 1024) {
            Message msg = new Message();
            Bundle bundle = new Bundle();
            ArrayList list = new ArrayList();

            list.add(decoded_asn1_in_xml);
            bundle.putParcelableArrayList("list", list);
            msg.what = ui_msg_asn1_decoded_ind;
            msg.setData(bundle);

            System.out.println("asn1 decoded: " + byte_num);
//            System.out.println(new String(decoded_asn1_in_xml).substring(0, byte_num));
        }
    }
}
