package pl.pwr.client;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

/**
 * Created by Evelan on 10/10/2016.
 */
public class Client {

    private Socket socket;

    public void invoke() throws IOException, ClassNotFoundException, InterruptedException {
        connect();
        sendMessages();
    }

    private void sendMessages() throws IOException, ClassNotFoundException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        while (true) {
            String message = scanner.next();
            if (message.equals("end")) {
                break;
            }
            objectOutputStream.writeObject(message);
        }
        objectOutputStream.close();
    }

    private void connect() {
        System.out.println("Connecting to server...");
        try {
            InetAddress host = InetAddress.getLocalHost();
            socket = new Socket(host.getHostName(), 9876);
        } catch (IOException e) {
            System.err.println("Failed: " + e.getMessage());
        }
        System.out.println("Connection successful");
    }
}