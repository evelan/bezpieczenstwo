package pl.pwr.server;

import pl.pwr.client.Client;
import pl.pwr.common.TalkFacade;
import pl.pwr.common.connection.listener.Listener;
import pl.pwr.common.connection.listener.ListenerImpl;
import pl.pwr.common.connection.sender.Sender;
import pl.pwr.common.connection.sender.SenderImpl;
import pl.pwr.common.model.Authorization;
import pl.pwr.common.model.EncryptionType;
import pl.pwr.common.model.KeyRequest;
import pl.pwr.common.model.SecretKey;
import pl.pwr.common.service.encryption.EncryptionFactory;
import pl.pwr.common.service.encryption.EncryptionProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Hashtable;
import java.util.Random;

import static pl.pwr.common.DHProtocol.calculateSecret;

/**
 * Created by Evelan on 12/10/2016.
 */
public class Server {

    private ServerSocket serverSocket;
    private Listener listener;
    private Sender sender;
    private Hashtable<String, Client> clientSockets;

    void invoke() throws Exception {
        clientSockets = new Hashtable<>();

        Thread thread = new Thread(() -> {
            while (true) {
                Socket socket = getClientSocket(9000);
                try {
                    connectClient(socket);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();
    }

    private void connectClient(Socket socket) throws Exception {
        sender = new SenderImpl(socket.getOutputStream());
        listener = new ListenerImpl(socket.getInputStream());
        Authorization authorization = requestAuthorization();

//        String id = String.valueOf(UUID.randomUUID());
//        Client client = Client.builder()
//                .authorization(authorization)
//                .socket(socket)
//                .sender(sender)
//                .listener(listener)
//                .id(id)
//                .build();
//        clientSockets.put(id, client);


        System.out.println("Starting talk...");
        TalkFacade talkFacade = new TalkFacade(listener, sender);
        talkFacade.startTalking(authorization, "Server");
    }


//    public void startServing(Authorization authorization, String name) {
//        try {
//            ListenerThread listenerThread = new ListenerThread(listener, authorization);
//            UserInputThread userInteraction = new UserInputThread(sender, authorization, name);
//
//            listenerThread.start();
//            userInteraction.start();
//            userInteraction.join();
//        } catch (Exception e) {
//            System.err.println("Error: " + e.getMessage());
//        }
//    }

    private Authorization requestAuthorization() throws IOException {
        KeyRequest keyRequest = listener.waitForKeysRequest();
        System.out.println("Received - keyRequest: " + keyRequest.getRequest());

        BigInteger verySecret = BigInteger.probablePrime(8, new Random());
        BigInteger p = BigInteger.probablePrime(8, new Random());
        BigInteger q = BigInteger.probablePrime(8, new Random());
        sender.sendPublicKeys(p.toString(), q.toString());
        System.out.println("Sending - public keys: " + p + ", " + q);

        SecretKey secretKey = listener.waitForSecretKey();
        System.out.println("Received - secret key: " + secretKey.getKey());

        String secretKeyToSend = calculateSecret(q, verySecret, p).toString();
        sender.sendSecretKey(secretKeyToSend);
        System.out.println("Sending - secret key: " + secretKeyToSend);

        BigInteger B = new BigInteger(secretKey.getKey());
        BigInteger s = calculateSecret(B, verySecret, p);
        System.out.println("S=" + s);

        EncryptionType encryptionType = listener.waitForEncryptionType();
        System.out.println("Received - encryption type: " + encryptionType);
        EncryptionProvider encryptionProvider = EncryptionFactory.getProvider(encryptionType);
        return new Authorization(encryptionProvider, s);
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
