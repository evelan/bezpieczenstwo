package pl.pwr.common.connection;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.Socket;

/**
 * Created by Evelan on 12/10/2016.
 */
public class ListenerThread extends Thread {

    private Socket socket;
    private Connector connector;

    public ListenerThread(Socket socket) {
        this.socket = socket;
        this.connector = new ConnectorImpl();
    }

    @Override
    public void run() {
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            String message;
            while (true) {
                message = (String) objectInputStream.readObject();

                if (message.equals("end")) {
                    break;
                }

                if (message.length() > 0) {
                    System.out.println("Odebrano: " + message);
                }
            }
            objectInputStream.close();
        } catch (ClassNotFoundException e1) {
            System.err.println("Message casting error");
        } catch (IOException e1) {
            System.err.println("Closing connection problem");
        }


    }
}
