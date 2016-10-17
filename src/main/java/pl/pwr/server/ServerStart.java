package pl.pwr.server;

public class ServerStart {

    public static void main(String args[]) {
        Server server = new Server();
        try {
            server.invoke();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}