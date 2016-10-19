package pl.pwr.common.connection;

import pl.pwr.common.connection.listener.Listener;
import pl.pwr.common.model.Message;
import pl.pwr.common.service.filter.MessageFilter;
import pl.pwr.common.service.filter.MessageFilterObserver;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evelan on 12/10/2016.
 */
public class ListenerThread extends Thread implements MessageFilterObserver {

    private Listener listener;
    private List<MessageFilter> messageFilterList;
    private boolean isListening;

    public ListenerThread(Listener listener) throws IOException {
        System.out.println("Listener started...");
        this.listener = listener;
        messageFilterList = new ArrayList<>();
        isListening = true;

    }

    @Override
    public void run() {
        while (isListening) {
            try {
                Message message = listener.waitForMessage();
                displayMessage(message);
            } catch (Exception e) {
                stopListening();
                e.printStackTrace();
            }
        }

    }

    private void displayMessage(Message message) {
        String plainText = message.getMsg();
        for (MessageFilter messageFilter : messageFilterList) {
            plainText = messageFilter.doFilter(plainText);
        }
        System.out.print("\n" + message.getFrom() + " says: " + plainText);
    }

    @Override
    public void registerMessageFilter(MessageFilter messageFilter) {
        messageFilterList.add(messageFilter);
    }

    @Override
    public void unregisterMessageFilter(MessageFilter messageFilter) {
        messageFilterList.remove(messageFilter);
    }

    public void stopListening() {
        isListening = false;
    }
}
