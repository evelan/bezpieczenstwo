package pl.pwr.server;

import java.io.IOException;

public class ServerStart {

    public static void main(String args[]) {
        Server server = new Server();
        try {
            server.invoke();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

}