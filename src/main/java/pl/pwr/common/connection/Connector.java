package pl.pwr.common.connection;

import java.net.Socket;

/**
 * Created by Evelan on 12/10/2016.
 */
public interface Connector {

    void sendMessage(String message, Socket socket);

    String waitForMessage(Socket socket);

}
