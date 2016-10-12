package pl.pwr.server;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Evelan on 12/10/2016.
 */
public class Server {

    private ServerSocket server;
    private Socket socket;

    public void invoke() throws IOException, ClassNotFoundException {
        waitForClients();
        waitForMessages();
    }

    private void waitForMessages() throws IOException, ClassNotFoundException {
        ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

        String message;
        while (true) {

            try {
                Thread.sleep(500);
                message = (String) objectInputStream.readObject();
            } catch (IOException ioe) {
                continue;
            } catch (InterruptedException e) {
                continue;
            }

            if (message.equals("end")) {
                break;
            }

            if (message.length() > 0) {
                System.out.println("Odebrano: " + message);
            }
        }

        objectInputStream.close();

    }

    private void waitForClients() {
        System.out.println("Waiting for client request");
        try {
            int SERVER_PORT = 9876;
            server = new ServerSocket(SERVER_PORT);
            socket = server.accept();
        } catch (IOException e) {
            System.out.println("Server error");
        }
    }

    private void closeConnection() {
        System.out.println("Close connection");
        try {
            socket.close();
            server.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
