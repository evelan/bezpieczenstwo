package pl.pwr.server;

public class ServerStart {

    public static void main(String args[]) {
        Server server = new Server();
        try {
            server.invoke(9000);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}