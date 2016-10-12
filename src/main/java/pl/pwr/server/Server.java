package pl.pwr.server;

import pl.pwr.common.connection.Connector;
import pl.pwr.common.connection.ConnectorImpl;

import java.io.IOException;
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

        Connector connector = new ConnectorImpl();

        String message;
        do {
            message = connector.waitForMessage(socket);
        } while (message.length() == 0);

        System.out.println("ODEBRANO: " + message);

        closeConnection();
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
