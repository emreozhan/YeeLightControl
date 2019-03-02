package Connection;

import Constant.DiscoverResponseType;
import Device.BulbDevice;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.*;
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

    private Socket clientSocket;
    private DataOutputStream outToServer;
    private BufferedReader inFromServer;

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
        BulbDevice device = new BulbDevice();

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
        device.setIp(receivePacket.getAddress().toString().substring(1));
        String location =responseData.get(DiscoverResponseType.LOCATION);
        device.setPort(Integer.parseInt(location.substring(location.lastIndexOf(":")+1)));
        device.setName(responseData.get(DiscoverResponseType.NAME));
        device.setId(responseData.get(DiscoverResponseType.ID));
        return device;
    }

    public boolean setTcpSetting(String ip, int port) {
        try {
            try {
                if (!clientSocket.isClosed()) {
                    clientSocket.close();
                }
            } catch (NullPointerException e) {
                System.out.println(e.getMessage());
            }
            clientSocket = new Socket(ip, port);
            outToServer = new DataOutputStream(clientSocket.getOutputStream());
            inFromServer = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public void toogleBulb(String command){
        String data = "NONE";
        try {
            outToServer.writeBytes("{\"id\":0,\"method\":" + command + "}" + "\r\n");
            data = inFromServer.readLine();
            System.out.println(data);
            //clientSocket.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
       // return data;

    }
}
