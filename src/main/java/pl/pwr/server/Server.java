package pl.pwr.server;

import pl.pwr.common.connection.sender.Sender;
import pl.pwr.common.connection.sender.SenderImpl;

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
        Sender sender = new SenderImpl(socket);
        waitForClients();
        sender.sendPublicKeys("1", "2");
        //Server listener thread waiting for secret key
        sender.sendSecretKey("23");
        waitForMessages();
        closeConnection();
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

    //TODO run this in thread
    /**
     * Opens socket server on port 9876
     */
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

    /**
     * Closing connection and server
     */
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
