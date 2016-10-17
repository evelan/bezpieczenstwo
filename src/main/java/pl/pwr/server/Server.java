package pl.pwr.server;

import pl.pwr.common.TalkFacade;
import pl.pwr.common.connection.sender.Sender;
import pl.pwr.common.connection.sender.SenderImpl;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Evelan on 12/10/2016.
 */
public class Server {

    private ServerSocket serverSocket;

    public void invoke() throws Exception {
        Socket socket = getClientSocket(9878);
        Sender sender = new SenderImpl(socket.getOutputStream());
//        sender.sendPublicKeys("1", "2");
        new TalkFacade(socket, sender).startTalking();
//        sender.sendSecretKey("23");
        closeConnection(socket);
    }

    private Socket getClientSocket(int portNumber) {
        System.out.println("Waiting for client request");
        Socket socket;
        try {
            serverSocket = new ServerSocket(portNumber);
            socket = serverSocket.accept();
            System.out.println("Client connected");
        } catch (IOException e) {
            socket = new Socket();
            System.out.println("Server error");
        }
        return socket;
    }

    /**
     * Closing connection and server
     */
    private void closeConnection(Socket socket) {
        System.out.println("Close connection");
        try {
            socket.close();
            serverSocket.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
