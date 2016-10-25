package pl.pwr.server;

public class ServerStart {

    private static short port = 9000;

    public static void main(String args[]) {
        configure(args);
        System.out.println("Running server on " + port + " port");
        Server server = new Server();
        try {
            server.invoke(port);
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }

    /**
     * Configuration port
     * @param args array with port number
     */
    private static void configure(String[] args) {
        if (args.length == 1) {
            try {
                port = Short.parseShort(args[0]);
            } catch (Exception e) {
                System.err.println("Wrong port number");
            }
        } else {
            System.out.println("Using default settings");
        }
    }

}