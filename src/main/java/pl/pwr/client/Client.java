package pl.pwr.client;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
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
import java.util.Random;
import java.util.Scanner;

import static pl.pwr.common.DHProtocol.calculateSecret;
import static pl.pwr.common.model.EncryptionType.NONE;

/**
 * Created by Evelan on 10/10/2016.
 */
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Client {

    Authorization authorization;
    String id;
    Socket socket;

    Sender sender;
    Listener listener;


    void invoke() throws Exception {
        socket = connectToServer("localhost", 9000);
        sender = new SenderImpl(socket.getOutputStream());
        listener = new ListenerImpl(socket.getInputStream());
        EncryptionType encryptionType = getUserEncryptionType();
        Authorization authorization = authorizeToServer(encryptionType);
        System.out.println("Starting talk...");
        TalkFacade talkFacade = new TalkFacade(listener, sender);
        talkFacade.startTalking(authorization, "anonymous");
    }

    private Authorization authorizeToServer(EncryptionType encryptionType) throws IOException {
        sender.sendKeysRequest();
        System.out.println("Sending - key request");
        PublicKey publicKey = listener.waitForPublicKeys();
        BigInteger p = new BigInteger(publicKey.getP());
        BigInteger q = new BigInteger(publicKey.getG());
        System.out.println("Received - public key: " + p + ", " + q);

        BigInteger verySecret = BigInteger.probablePrime(8, new Random());
        String secretKeyToSend = calculateSecret(q, verySecret, p).toString();
        sender.sendSecretKey(secretKeyToSend);
        System.out.println("Sending - secret key: " + secretKeyToSend);

        SecretKey secretKey = listener.waitForSecretKey();
        System.out.println("Received - secret key: " + secretKey.getKey());

        BigInteger A = new BigInteger(secretKey.getKey());
        BigInteger s = calculateSecret(A, verySecret, p);
        System.out.println("S=" + s);

        sender.sendEncryptionType(encryptionType);
        System.out.println("Sending - encryptionType: " + encryptionType);
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