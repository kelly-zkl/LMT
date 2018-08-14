package diag;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by xxx on 2018/1/27.
 */

public class ui_cmd_req_send {

    static private DatagramSocket socket;
    static private InetAddress address;
    static private int port = 8848;

    static public void init() {

        try {
            socket = new DatagramSocket();
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            address = InetAddress.getLocalHost();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    static public void send(byte[] cmd) {

        DatagramPacket packet = new DatagramPacket(cmd, cmd.length, address, port);

        try {
            socket.send(packet);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
