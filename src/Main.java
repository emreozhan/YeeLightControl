import Connection.ConnectionClient;
import Device.BulbDevice;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static final String multicastChannel = "239.255.255.250";
    public static final int port = 1982;
    public static final int timeout = 15000;
    public static List<BulbDevice> devices;

    public static void main(String[] args) {
        System.out.println("Hello YeeLight!");
        devices=new ArrayList<>();

        byte[] sendData = ("M-SEARCH * HTTP/1.1\r\n" +
                "HOST: 239.255.255.250:1982\r\n" +
                "MAN: \"ssdp:discover\"\r\n" +
                "ST: wifi_bulb").getBytes();

        try {
            ConnectionClient client = new ConnectionClient(sendData, InetAddress.getByName(multicastChannel), port, timeout);

            DatagramPacket receivePacket=null;
            while (receivePacket == null) {
                receivePacket = client.DiscoverBulbs();
                Thread.sleep(5000);// wait 5 sec
            }
            devices.add(client.PrepareDeviceByReceivedPacket(receivePacket));

        } catch (Exception e) {

        }
        ;

    }
}
