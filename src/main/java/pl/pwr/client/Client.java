package pl.pwr.client;

import pl.pwr.common.connection.Connector;
import pl.pwr.common.connection.ConnectorImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Scanner;

/**
 * Created by Evelan on 10/10/2016.
 */
public class Client {

    private Socket socket;

    public void invoke() throws IOException, ClassNotFoundException, InterruptedException {
        connect();

        Connector connector = new ConnectorImpl();

        Scanner scanner = new Scanner(System.in);
        String message = scanner.next();
        connector.sendMessage(message, socket);
    }

    private void connect() {
        System.out.println("Connecting to server...");
        try {
            InetAddress host = InetAddress.getLocalHost();
            socket = new Socket(host.getHostName(), 9876);
        } catch (IOException e) {
            e.printStackTrace();
            System.err.println("Connection failed: " + e.getMessage());
        }
        System.out.println("Connection successful");
    }
}