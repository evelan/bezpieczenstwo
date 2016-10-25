package pl.pwr.server;

import pl.pwr.common.TalkFacade;
import pl.pwr.common.connection.listener.Listener;
import pl.pwr.common.connection.listener.ListenerImpl;
import pl.pwr.common.connection.sender.Sender;
import pl.pwr.common.connection.sender.SenderImpl;
import pl.pwr.common.model.Authorization;
import pl.pwr.common.model.EncryptionType;
import pl.pwr.common.model.SecretKey;
import pl.pwr.common.service.encryption.EncryptionFactory;
import pl.pwr.common.service.encryption.EncryptionProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static pl.pwr.common.DHProtocol.calculateSecret;
import static pl.pwr.common.DHProtocol.generatePrime;

/**
 * Created by Evelan on 12/10/2016.
 */
public class Server {

    private ServerSocket serverSocket;
    private Listener listener;
    private Sender sender;

    void invoke(int port) throws Exception {
        Socket socket = getClientSocket(port);
        connectClient(socket);
    }

    private void connectClient(Socket socket) throws Exception {
        sender = new SenderImpl(socket.getOutputStream());
        listener = new ListenerImpl(socket.getInputStream());

        Authorization authorization;
        try {
            authorization = requestAuthorization();
        } catch (Exception e) {
            System.err.println("Authorization problem, encryption: NONE -> " + e.getMessage());
            authorization = new Authorization();
        }

        System.out.println("Starting talk...");
        TalkFacade talkFacade = new TalkFacade(listener, sender);
        talkFacade.startTalking(authorization, "Server");
    }

    private Authorization requestAuthorization() throws IOException, InterruptedException, ExecutionException, TimeoutException {
        listener.waitForKeysRequest();

        BigInteger verySecret = generatePrime();
        BigInteger p = generatePrime();
        BigInteger q = generatePrime();
        sender.sendPublicKeys(p.toString(), q.toString());

        CompletableFuture<SecretKey> secretKey =
                CompletableFuture.supplyAsync(() -> listener.waitForSecretKey());

        String secretKeyToSend = calculateSecret(q, verySecret, p).toString();
        sender.sendSecretKey(secretKeyToSend);

        BigInteger B = new BigInteger(secretKey.get(5, TimeUnit.SECONDS).getKey());
        BigInteger s = calculateSecret(B, verySecret, p);

        EncryptionType encryptionType = listener.waitForEncryptionType();
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
