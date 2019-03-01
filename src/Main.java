public class Main {

    public static void main(String[] args) {
        System.out.println("Hello YeeLight!");

        byte[] sendData = ("M-SEARCH * HTTP/1.1\r\n" +
                "HOST: 239.255.255.250:1982\r\n" +
                "MAN: \"ssdp:discover\"\r\n" +
                "ST: wifi_bulb").getBytes();
    }
}
