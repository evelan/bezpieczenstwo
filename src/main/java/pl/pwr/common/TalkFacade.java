package pl.pwr.common;

import pl.pwr.common.connection.ListenerThread;
import pl.pwr.common.connection.UserInputThread;
import pl.pwr.common.connection.listener.Listener;
import pl.pwr.common.connection.sender.Sender;

/**
 * Created by Evelan on 17/10/2016.
 */
public class TalkFacade {


    private final Sender sender;
    private final Listener listener;

    public TalkFacade(Listener listener, Sender sender) {
        this.listener = listener;
        this.sender = sender;
    }

    public void startTalking() {
        try {
            ListenerThread listenerThread = new ListenerThread(listener);
            UserInputThread userInteraction = new UserInputThread(sender);

            listenerThread.start();
            userInteraction.start();
            userInteraction.join();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
