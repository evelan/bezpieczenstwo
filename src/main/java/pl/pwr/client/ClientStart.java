package pl.pwr.client;

/**
 * Created by Evelan on 12/10/2016.
 */
public class ClientStart {

    private static String address = "localhost";
    private static short port = 9000;

    public static void main(String[] args) {
        configure(args);
        System.out.println("Looking for server on " + address + ":" + port);
        Client client = new Client();
        try {
            client.invoke(address, port);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Configuration hostAddress and port
     * @param args array with hostAddress (first) and port number (second)
     */
    private static void configure(String[] args) {
        if (args.length == 2) {
            address = args[0];
            try {
                port = Short.parseShort(args[1]);
            } catch (Exception e) {
                System.err.println("Wrong port number");
            }
        } else {
            System.out.println("Using default settings");
        }
    }
}
