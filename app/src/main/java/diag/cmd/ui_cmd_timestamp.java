package diag.cmd;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;

import diag.ui_cmd_code;

/**
 * Created by xxx on 2018/1/28.
 */

public class ui_cmd_timestamp extends Object {

    public int req_code = ui_cmd_code.cmd_code_get_timestamp;
    public int rsp_code;
    public int time_stamp1;
    public int time_stamp2;

    public byte[] to_bytes() {

        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutput out = null;

        try {
            out = new ObjectOutputStream(bos);
            out.write(req_code);
            out.flush();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return bos.toByteArray();
    }

    public void toObject(byte[] bytes) {

        ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
        ObjectInput in = null;

        try {
            in = new ObjectInputStream(bis);
            rsp_code = in.readInt();
            time_stamp1 = in.readInt();
            time_stamp2 = in.readInt();
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            if (in != null) {
                in.close();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
};
