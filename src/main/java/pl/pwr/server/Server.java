package pl.pwr.server;

import pl.pwr.common.TalkFacade;
import pl.pwr.common.connection.listener.Listener;
import pl.pwr.common.connection.listener.ListenerImpl;
import pl.pwr.common.connection.sender.Sender;
import pl.pwr.common.connection.sender.SenderImpl;
import pl.pwr.common.model.Encryption;
import pl.pwr.common.model.KeyRequest;
import pl.pwr.common.model.SecretKey;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * Created by Evelan on 12/10/2016.
 */
public class Server {

    private ServerSocket serverSocket;
    private Listener listener;
    private Sender sender;

    public void invoke() throws Exception {
        Socket socket = getClientSocket(9878);
        sender = new SenderImpl(socket.getOutputStream());
        listener = new ListenerImpl(socket.getInputStream());

        requestAuthorization();


        System.out.println("Starting talk...");
        TalkFacade talkFacade = new TalkFacade(listener, sender);
        talkFacade.startTalking();
    }

    private void requestAuthorization() throws IOException {
        KeyRequest keyRequest = listener.waitForKeysRequest();
        System.out.println("Received - keyRequest: " + keyRequest.getRequest());

        Integer verySecret = 11;
        Integer p = 847841;
        Integer q = 1423841;
        sender.sendPublicKeys(p.toString(), q.toString());
        System.out.println("Sending - public keys: " + p + ", " + q);

        SecretKey secretKey = listener.waitForSecretKey();
        System.out.println("Received - secret key: " + secretKey.getKey());

        String secretKeyToSend = String.valueOf(q ^ verySecret % p);
        sender.sendSecretKey(secretKeyToSend);
        System.out.println("Sending - secret key: " + secretKeyToSend);

        Integer B = Integer.valueOf(secretKey.getKey());
//        B^a mod p = s
        Integer s = B ^ verySecret % p;
        System.out.println("S=" + s);

        Encryption clientEncryption = listener.waitForEncryptionType();
        System.out.println("Received - encryption type: " + clientEncryption.getEncryption());

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
