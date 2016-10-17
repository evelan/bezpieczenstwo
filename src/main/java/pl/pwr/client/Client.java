package pl.pwr.client;

import pl.pwr.common.TalkFacade;
import pl.pwr.common.connection.sender.Sender;
import pl.pwr.common.connection.sender.SenderImpl;

import java.io.IOException;
import java.net.Socket;

import static pl.pwr.common.model.EncryptionType.NONE;

/**
 * Created by Evelan on 10/10/2016.
 */
public class Client {

    public void invoke() throws Exception {
        Socket socket = connectToServer("localhost", 9878);
        Sender sender = new SenderImpl(socket.getOutputStream());

        //waitForPublicKeys();
        //wait for secret key

        sender.sendKeysRequest();
        sender.sendSecretKey("");
        sender.sendEncryptionType(NONE);

        new TalkFacade(socket, sender).startTalking();
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