package pl.pwr.common.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Evelan on 12/10/2016.
 */
public class ConnectorImpl implements Connector {

    public void sendMessage(String message, Socket socket) {
        try {
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            objectOutputStream.writeObject(message);
            objectOutputStream.close();
        } catch (IOException e) {
            System.err.println("Closing connection problem");
        }
    }

    public String waitForMessage(Socket socket) {
        String message = "";
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());
            message = (String) objectInputStream.readObject();
            objectInputStream.close();
        } catch (ClassNotFoundException e) {
            System.err.println("Message casting error");
        } catch (IOException e) {
            System.err.println("Closing connection problem");
        }
        return message;
    }
}
