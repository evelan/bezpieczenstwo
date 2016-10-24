package pl.pwr.common.connection;

import pl.pwr.common.connection.sender.Sender;
import pl.pwr.common.model.Authorization;
import pl.pwr.common.service.encryption.EncryptionProvider;

import java.math.BigInteger;
import java.util.Scanner;

/**
 * Created by Evelan on 15/10/2016.
 */
public class UserInputThread extends Thread {

    private Scanner scanner;
    private Sender sender;
    private boolean isInteracting;
    private EncryptionProvider encryptionProvider;
    private BigInteger key;
    private String name;

    public UserInputThread(Sender sender, Authorization authorization, String name) {
        scanner = new Scanner(System.in);
        this.sender = sender;
        isInteracting = true;
        this.encryptionProvider = authorization.getEncryptionProvider();
        this.key = authorization.getKey();
        this.name = name;
    }

    @Override
    public void run() {
        while (isInteracting) {
            try {
                String plainMessage = scanner.next();
                String encryptedMessage = encryptionProvider.encrypt(plainMessage, key);
                sender.sendMessage(encryptedMessage, name);
                System.out.println("Message sent: " + plainMessage);
            } catch (Exception e) {
                e.printStackTrace();
                stopInteracting();
            }
        }
    }

    public void stopInteracting() {
        isInteracting = false;
    }
}
