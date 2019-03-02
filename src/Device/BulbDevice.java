package Device;

public class BulbDevice {

    private String id;
    private String name;
    private String ip;
    private int port;
    private String availableMethods;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getIp() {
        return ip;
    }

    public void setIp(String ip) {
        this.ip = ip;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public String getAvailableMethods() {
        return availableMethods;
    }

    public void setAvailableMethods(String availableMethods) {
        this.availableMethods = availableMethods;
    }
}
