import Connection.ConnectionClient;
import Device.BulbDevice;
import com.sun.xml.internal.bind.v2.runtime.reflect.opt.Const;

import java.net.DatagramPacket;
import java.net.InetAddress;
import java.util.ArrayList;
import java.util.List;

public class Main {

    public static void main(String[] args) {

        System.out.println("Device Added");
        try {
            Dashboard window = new Dashboard();
            window.setVisible(true);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
