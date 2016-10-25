package pl.pwr.client;

import org.apache.commons.lang3.StringUtils;
import pl.pwr.common.TalkFacade;
import pl.pwr.common.connection.listener.Listener;
import pl.pwr.common.connection.listener.ListenerImpl;
import pl.pwr.common.connection.sender.Sender;
import pl.pwr.common.connection.sender.SenderImpl;
import pl.pwr.common.model.Authorization;
import pl.pwr.common.model.EncryptionType;
import pl.pwr.common.model.PublicKey;
import pl.pwr.common.model.SecretKey;
import pl.pwr.common.service.encryption.EncryptionFactory;
import pl.pwr.common.service.encryption.EncryptionProvider;

import java.io.IOException;
import java.math.BigInteger;
import java.net.Socket;
import java.util.Scanner;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;

import static pl.pwr.common.DHProtocol.calculateSecret;
import static pl.pwr.common.DHProtocol.generatePrime;
import static pl.pwr.common.model.EncryptionType.NONE;

/**
 * Created by Evelan on 10/10/2016.
 */

public class Client {

    private Sender sender;
    private Listener listener;

    void invoke(String host, int port) throws Exception {
        Socket socket = connectToServer(host, port);
        sender = new SenderImpl(socket.getOutputStream());
        listener = new ListenerImpl(socket.getInputStream());
        EncryptionType encryptionType = getUserEncryptionType();

        Authorization authorization;
        try {
            authorization = authorizeToServer(encryptionType);
        } catch (Exception e) {
            System.err.println("Authorization problem - encryption: NONE");
            authorization = new Authorization();
        }

        System.out.println("Starting talk...");
        TalkFacade talkFacade = new TalkFacade(listener, sender);
        talkFacade.startTalking(authorization, "Client");
    }

    private Authorization authorizeToServer(EncryptionType encryptionType) throws IOException, InterruptedException, ExecutionException, TimeoutException {
        sender.sendKeysRequest();
        PublicKey publicKey = listener.waitForPublicKeys();
        BigInteger p = new BigInteger(publicKey.getP());
        BigInteger q = new BigInteger(publicKey.getG());

        BigInteger verySecret = generatePrime();
        String secretKeyToSend = calculateSecret(q, verySecret, p).toString();

        CompletableFuture<SecretKey> secretKey =
                CompletableFuture.supplyAsync(() -> listener.waitForSecretKey());

        sender.sendSecretKey(secretKeyToSend);

        BigInteger A = new BigInteger(secretKey.get(5, TimeUnit.SECONDS).getKey());
        BigInteger s = calculateSecret(A, verySecret, p);

        sender.sendEncryptionType(encryptionType);
        EncryptionProvider encryptionProvider = EncryptionFactory.getProvider(encryptionType);
        return new Authorization(encryptionProvider, s);
    }


    private EncryptionType getUserEncryptionType() {
        EncryptionType encryptionType;
        try {
            System.out.println("Choose encryption: XOR, CAESAR, NONE");
            String dirtyUserInput = getUserInput();
            dirtyUserInput = StringUtils.trim(dirtyUserInput);
            dirtyUserInput = StringUtils.upperCase(dirtyUserInput);
            encryptionType = EncryptionType.valueOf(dirtyUserInput);
        } catch (Exception e) {
            System.out.println("Wrong input -> setting NONE encryption");
            encryptionType = NONE;
        }

        return encryptionType;
    }

    private String getUserInput() {
        Scanner scanner = new Scanner(System.in);
        return scanner.next();
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