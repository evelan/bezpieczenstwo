package pl.pwr.common;

import pl.pwr.common.connection.ListenerThread;
import pl.pwr.common.connection.UserInputThread;
import pl.pwr.common.connection.sender.Sender;

import java.net.Socket;

/**
 * Created by Evelan on 17/10/2016.
 */
public class TalkFacade {

    private Socket socket;
    private Sender sender;

    public TalkFacade(Socket socket, Sender sender) {
        this.socket = socket;
        this.sender = sender;
    }

    public void startTalking() {
        try {
            ListenerThread listenerThread = new ListenerThread(socket.getInputStream());
            listenerThread.start();
            UserInputThread userInteraction = new UserInputThread(sender);
            userInteraction.start();
            userInteraction.join();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
