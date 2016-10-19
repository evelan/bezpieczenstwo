package pl.pwr.client;

import pl.pwr.common.TalkFacade;
import pl.pwr.common.connection.listener.Listener;
import pl.pwr.common.connection.listener.ListenerImpl;
import pl.pwr.common.connection.sender.Sender;
import pl.pwr.common.connection.sender.SenderImpl;
import pl.pwr.common.model.EncryptionType;
import pl.pwr.common.model.PublicKey;
import pl.pwr.common.model.SecretKey;

import java.io.IOException;
import java.net.Socket;

import static pl.pwr.common.model.EncryptionType.NONE;

/**
 * Created by Evelan on 10/10/2016.
 */
public class Client {

    private Sender sender;
    private Listener listener;

    public void invoke() throws Exception {
        Socket socket = connectToServer("localhost", 9878);
        sender = new SenderImpl(socket.getOutputStream());
        listener = new ListenerImpl(socket.getInputStream());

        authorizeToServer();

        System.out.println("Starting talk...");
        TalkFacade talkFacade = new TalkFacade(listener, sender);
        talkFacade.startTalking();
    }

    private void authorizeToServer() throws IOException {
        sender.sendKeysRequest();
        System.out.println("Sending - key request");

        PublicKey publicKey = listener.waitForPublicKeys();
        Integer p = Integer.valueOf(publicKey.getP());
        Integer q = Integer.valueOf(publicKey.getG());
        System.out.println("Received - public key: " + p + ", " + q);

        Integer verySecret = 331;
        String secretKeyToSend = String.valueOf(q ^ verySecret % p);
        sender.sendSecretKey(secretKeyToSend);
        System.out.println("Sending - secret key: " + secretKeyToSend);

        SecretKey secretKey = listener.waitForSecretKey();
        System.out.println("Received - secret key: " + secretKey.getKey());

        Integer A = Integer.valueOf(secretKey.getKey());
        Integer s = A ^ verySecret % p;
        System.out.println("S=" + s);

        EncryptionType encryptionType = NONE;
        sender.sendEncryptionType(encryptionType);
        System.out.println("Sending - encryptionType: " + encryptionType);

    }

    private Socket connectToServer(String host, int portNumber) {
        System.out.println("Connecting to server...");
        Socket socket;
        try {
            socket = new Socket(host, portNumber);
        } catch (IOException e) {
            socket = new Socket();
            System.err.println("Failed: " + e.getMessage());
        }
        System.out.println("Connection successful");
        return socket;
    }
}