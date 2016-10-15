package pl.pwr.client;

import pl.pwr.common.connection.sender.Sender;
import pl.pwr.common.connection.sender.SenderImpl;
import pl.pwr.common.service.encryption.DiffieHellmanProtocol;
import pl.pwr.common.service.encryption.DiffieHellmanProtocolImpl;
import pl.pwr.common.service.interaction.UserInteractionService;
import pl.pwr.common.service.interaction.UserInteractionServiceImpl;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.InetAddress;
import java.net.Socket;
import java.util.Scanner;

import static java.util.Base64.getEncoder;
import static pl.pwr.common.model.EncryptionType.NONE;

/**
 * Created by Evelan on 10/10/2016.
 */
public class Client {

    private Socket socket;

    public void invoke() throws IOException, ClassNotFoundException, InterruptedException {

        UserInteractionService userInteraction = new UserInteractionServiceImpl();
        DiffieHellmanProtocol encoder = new DiffieHellmanProtocolImpl();
        Sender sender = new SenderImpl(socket);

        connect();
        waitForPublicKeys();
        //Start client listener thread waiting for secret key
        sender.sendSecretKey("");
        sender.sendEncryptionType(NONE);

        while (true) {
            String plainMessage = userInteraction.getInputFromUser();
            String encryptedMessage = encoder.encrypt(plainMessage);
            encryptedMessage = getEncoder().encodeToString(encryptedMessage.getBytes());
            sender.sendMessage(encryptedMessage);
        }

//        sendMessages();
    }

    private void waitForPublicKeys() {

    }

    private void sendMessages() throws IOException, ClassNotFoundException, InterruptedException {
        Scanner scanner = new Scanner(System.in);
        ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());

        while (true) {
            String message = scanner.next();
            if (message.equals("end")) {
                break;
            }
            objectOutputStream.writeObject(message);
        }
        objectOutputStream.close();
    }

    private void connect() {
        System.out.println("Connecting to server...");
        try {
            InetAddress host = InetAddress.getLocalHost();
            socket = new Socket(host.getHostName(), 9876);
        } catch (IOException e) {
            System.err.println("Failed: " + e.getMessage());
        }
        System.out.println("Connection successful");
    }
}