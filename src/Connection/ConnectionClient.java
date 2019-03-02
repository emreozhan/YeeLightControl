package Connection;

import Device.BulbDevice;

import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

public class ConnectionClient {

    private byte[] sendData = new byte[1024];
    private byte[] receiveData = new byte[1024];
    private int port;
    private InetAddress multicastAddress;
    private int timeOut;

    public ConnectionClient(byte[] sendData, InetAddress multicastAddress, int port, int timeout) {
        this.sendData = sendData;
        this.multicastAddress = multicastAddress;
        this.port = port;
        this.timeOut = timeout;
    }


    public DatagramPacket DiscoverBulbs() throws SocketException {
        List<BulbDevice> devices = new ArrayList<BulbDevice>();
        try {
            DatagramPacket sendPacket = new DatagramPacket(sendData, sendData.length,
                    multicastAddress, port);

            DatagramSocket clientSocket = new DatagramSocket();
            clientSocket.setSoTimeout(timeOut);
            clientSocket.send(sendPacket);

            DatagramPacket receivePacket = new DatagramPacket(receiveData, receiveData.length);

            clientSocket.receive(receivePacket);
            System.out.println("Socket receive");

            return receivePacket;

        } catch (Exception e) {

            System.out.println(e.getMessage());
        }
        return null;
    }

    public BulbDevice PrepareDeviceByReceivedPacket(DatagramPacket receivePacket) {
        BulbDevice devi = new BulbDevice();

        Scanner sc = new Scanner(new String(receivePacket.getData()));

        HashMap<String, String> responseData = new HashMap<>();
        String dummyLine = sc.nextLine();
        String[] dummies;
        responseData.put("HTTPStatus", dummyLine);

        while (sc.hasNext()) {
            dummyLine = sc.nextLine();
            dummies = dummyLine.split(":", 2);
            if (dummyLine.contains(":"))
                responseData.put(dummies[0], dummies[1]);
        }

        return devi;
    }

}
