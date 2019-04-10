package main;

/*This is code written by Frederik Alexander Hounsvad
 * The use of this code in a non commercial and non exam environment is permitted
 */
/**
 *
 * @author Pinnacle F
 */
public class Device {

    private final static boolean DEBUG = true;
    /**
     * The display name of the device
     */
    private String name;

    /**
     * The MAC/physical address of the device
     */
    private String mac;

    /**
     * The ip of the device or the ip of the router to which to send the command
     */
    private String ip;

    /**
     * The port of the device or of the router to which to send the command
     */
    private int port;

    /**
     * For details see {@link Device#update}
     *
     * @param name
     * @param mac
     * @param ip
     * @param port
     * @throws IllegalArgumentException
     */
    public Device(String name, String mac, String ip, Integer port) throws IllegalArgumentException {
        update(name, mac, ip, port);
    }

    /**
     * For details see {@link Device#update}
     *
     * @param name
     * @param mac
     * @param ip
     */
    public Device(String name, String mac, String ip) {
        this(name, mac, ip, 7);
    }

    /**
     * For details see {@link Device#update}
     *
     * @param name
     * @param mac
     */
    public Device(String name, String mac) {
        this(name, mac, "255.255.255.255", 7);
    }

    /**
     * Generic getter
     *
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * Generic getter
     *
     * @return the mac
     */
    public String getMac() {
        return mac;
    }

    /**
     * Generic getter
     *
     * @return the ip
     */
    public String getIp() {
        return ip;
    }

    /**
     * Generic getter
     *
     * @return the port
     */
    public int getPort() {
        return port;
    }

    /**
     * Updates the values associated with a device
     * <p>
     * Pass null to keep a variable at its current value
     *
     * @param name is the name of the device
     * @param mac  is the mac of the device
     * @param ip   is the ip of the device
     * @param port is the port of the device
     * @throws IllegalArgumentException upon a wrong input
     */
    public final void update(String name, String mac, String ip, Integer port) throws IllegalArgumentException {
        StringBuilder error = new StringBuilder();
        if (name != null && name.matches("([0-9,A-Å,a-å]*)")) {
            this.name = name;
        } else {
            System.out.print((DEBUG && name != null ? "The illegal name was: " + name + "\n" : (DEBUG ? "Name was null" : "")));
            error.append(":The name contained one or more illegal characters:");
        }
        if (mac != null && mac.matches("([A-F0-9]{2}[-:][A-F0-9]{2}[-:][A-F0-9]{2}[-:][A-F0-9]{2}[-:][A-F0-9]{2}[-:][A-F0-9]{2})")) {
            this.mac = mac;
        } else {
            System.out.print((DEBUG && mac != null ? "The illegal MAC was: " + mac + "\n" : (DEBUG ? "MAC was null" : "")));
            error.append(":The was invalid:");
        }
        if (ip != null && ip.matches("([0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3}[.][0-9]{1,3})")) {
            String[] tokens = ip.split(".");
            for (int i = 0; i < tokens.length; i++) {
                if (tokens[i].length() == 2) {
                    tokens[i] = "0" + tokens[i];
                } else if (tokens[i].length() == 1) {
                    tokens[i] = "00" + tokens[i];
                }
            }
            ip = "";
            for (String s : tokens) {
                ip += s;
            }
            this.ip = ip;
        } else {
            System.out.print((DEBUG && ip != null ? "The illegal ip was: " + ip + "\n" : (DEBUG ? "ip was null" : "")));
            error.append(":The ip was invalid:");
        }
        if (port != null && port < 65535 && port > 0) {
            this.port = port;
        } else {
            System.out.print((DEBUG && port != null ? "The illegal port was: " + port + "\n" : (DEBUG ? "port was null" : "")));
            error.append(":The port was out of bounds:");
        }
        if (!error.toString().equalsIgnoreCase("")) {
            //throw new IllegalArgumentException(error.toString());
        }
    }

    @Override
    public String toString() {
        return String.format("%s %s%n%s", name, mac, ip);
    }

}
