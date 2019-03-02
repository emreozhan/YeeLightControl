import Connection.ConnectionClient;
import Device.BulbDevice;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;

public class Dashboard extends JFrame {
    public JFrame frame;
    public JButton btnDiscoverBulb;
    public JButton btnToogleBulb;
    public JPanel MePanel;
    private JButton btnConnect;

    public static final String multicastChannel = "239.255.255.250";
    public static final int port = 1982;
    public static final int timeout = 15000;

    public static ConnectionClient client;
    public static BulbDevice device;

    public Dashboard() {
        add(MePanel);
        setTitle("emre");
        setSize(400, 600);

        btnDiscoverBulb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {

                System.out.println("Hello YeeLight!");
                device = new BulbDevice();

                byte[] sendData = ("M-SEARCH * HTTP/1.1\r\n" +
                        "HOST: 239.255.255.250:1982\r\n" +
                        "MAN: \"ssdp:discover\"\r\n" +
                        "ST: wifi_bulb").getBytes();

                try {
                    client = new ConnectionClient(sendData, InetAddress.getByName(multicastChannel), port, timeout);

                    DatagramPacket receivePacket = null;
                    while (receivePacket == null) {
                        receivePacket = client.DiscoverBulbs();
                        Thread.sleep(5000);// wait 5 sec
                    }
                    device = client.PrepareDeviceByReceivedPacket(receivePacket);
                    System.out.println("Device Added");

                } catch (Exception ex) {

                }
            }
        });
        btnConnect.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                client.setTcpSetting(device.getIp(), device.getPort());
            }
        });
        btnToogleBulb.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //client.toogleBulb("\"set_power\",\"params\":[\"" + "on" + "\", \"" + "sudden" + "\", " + 30 + "]");
                client.toogleBulb("\"toggle\",\"params\":[\"" + "" + "\", \"" + "" + "\", "  + "]");


            }
        });

    }


}
