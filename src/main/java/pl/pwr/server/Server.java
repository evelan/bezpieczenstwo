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
import java.util.concurrent.TimeUnit;

import static pl.pwr.common.DHProtocol.calculateSecret;
import static pl.pwr.common.DHProtocol.generatePrime;

/**
 * Created by Evelan on 12/10/2016.
 */
public class Server {

    private Listener listener;
    private Sender sender;

    /**
     * Acquire client socket and connect client
     *
     * @param serverPortNumber - server address
     * @throws Exception
     */
    void invoke(int serverPortNumber) throws Exception {
        Socket clientSocket = getClientSocket(serverPortNumber);
        connectClient(clientSocket);
    }

    /**
     * Connecting client
     *
     * @param clientSocket - clientSocket
     * @throws Exception
     */
    private void connectClient(Socket clientSocket) throws Exception {
        sender = new SenderImpl(clientSocket.getOutputStream());
        listener = new ListenerImpl(clientSocket.getInputStream());

        Authorization clientAuthorization;
        try {
            clientAuthorization = requestAuthorization();
        } catch (Exception e) {
            System.err.println("Authorization problem, encryption: NONE -> " + e.getMessage());
            clientAuthorization = new Authorization();
        }

        System.out.println("Starting talk...");
        TalkFacade talkFacade = new TalkFacade(listener, sender);
        talkFacade.startTalking(clientAuthorization, "Server");
    }

    /**
     * Authorize client, Diffie-Hellman protocol
     *
     * @return authorization with populated EncryptionProvider and key
     * @throws Exception
     */
    private Authorization requestAuthorization() throws Exception {
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

        boolean lessThanZero = s.signum() != 1;
        if (lessThanZero) {
            throw new IllegalArgumentException("Secret key <0");
        }

        EncryptionType encryptionType = listener.waitForEncryptionType();
        EncryptionProvider encryptionProvider = EncryptionFactory.getProvider(encryptionType);
        return new Authorization(encryptionProvider, s);
    }


    /**
     * Getting client socket
     *
     * @param clientPortNumber
     * @return client socket
     */
    private Socket getClientSocket(int clientPortNumber) throws IOException {
        System.out.println("Waiting for client request");
        ServerSocket serverSocket = new ServerSocket(clientPortNumber);
        Socket socket = serverSocket.accept();
        System.out.println("Client connected");
        return socket;
    }
}
