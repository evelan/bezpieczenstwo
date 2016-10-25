package pl.pwr.common;

import pl.pwr.common.connection.ListenerThread;
import pl.pwr.common.connection.UserInputThread;
import pl.pwr.common.connection.listener.Listener;
import pl.pwr.common.connection.sender.Sender;
import pl.pwr.common.model.Authorization;
import pl.pwr.common.service.filter.TestMessageFilter;

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

    public void startTalking(Authorization authorization, String name) {
        try {
            ListenerThread listenerThread = new ListenerThread(listener, authorization);
            UserInputThread userInteraction = new UserInputThread(sender, authorization, name);

            listenerThread.registerMessageFilter(new TestMessageFilter());

            listenerThread.start();
            userInteraction.start();
            userInteraction.join();
        } catch (Exception e) {
            System.err.println("Error: " + e.getMessage());
        }
    }
}
