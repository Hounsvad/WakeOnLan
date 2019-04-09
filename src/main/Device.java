package main;

/*This is code written by Frederik Alexander Hounsvad
 * The use of this code in a non commercial and non exam environment is permitted
 */
/**
 *
 * @author Pinnacle F
 */
public class Device {

    private String name;

    private String mac;

    private byte[] ip;

    private int port;

    public Device(String name, String mac, byte[] ip, int port) {
        this.name = name;
        this.mac = mac;
        this.ip = ip;
        this.port = port;
    }

    public Device(String name, String mac, byte[] ip) {
        this.name = name;
        this.mac = mac;
        this.ip = ip;
        this.port = 7;
    }

    public Device(String name, String mac) {
        this.name = name;
        this.mac = mac;
        this.ip = new byte[]{Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE, Byte.MAX_VALUE};
        this.port = 7;
    }

    public String getName() {
        return name;
    }

    public String getMac() {
        return mac;
    }

    public byte[] getIp() {
        return ip;
    }

    public int getPort() {
        return port;
    }

    public void update(String name, String mac, byte[] ip, int port) {
        if (name.matches("([0-9,A-Å,a-å]*)")) {
            this.name = name;
        }
        if (mac.matches("([A-F0-9]{2}[-][A-F0-9]{2}[-][A-F0-9]{2}[-][A-F0-9]{2}[-][A-F0-9]{2}[-][A-F0-9]{2})")) {
            this.mac = mac;
        }
        this.ip = ip;
        if (ip.length == 4 && ip[0] ==) {
            this.port = port;
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s%n%s", name, mac, ip);
    }

}
