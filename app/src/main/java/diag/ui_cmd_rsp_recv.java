package diag;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

/**
 * Created by xxx on 2018/1/27.
 */


public class ui_cmd_rsp_recv extends Thread {

    private String name;
    private byte[] data;
    DatagramSocket socket = null;
    DatagramPacket packet = null;

    public ui_cmd_rsp_recv(String name) {

        this.name = name;
        data = new byte[10240];

        try {
            socket = new DatagramSocket(8849, InetAddress.getByName("127.0.0.1"));
            socket.setBroadcast(true);
        } catch (Exception e) {
            e.printStackTrace();
        }

        packet = new DatagramPacket(data, data.length);
    }

    public void run() {

        System.out.println("ui cmd rsp recv started.");

        for (; ; ) {

            try {
                socket.receive(packet);
            } catch (Exception e) {
                e.printStackTrace();
            }

            ui_cmd_agent.cmd_rsp_ind(data);
        }
    }
}
