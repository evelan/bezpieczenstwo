package pl.pwr.common.connection;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.pwr.common.model.Message;
import pl.pwr.common.service.filter.MessageFilter;
import pl.pwr.common.service.filter.MessageFilterObserver;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

/**
 * Created by Evelan on 12/10/2016.
 */
public class ListenerThread extends Thread implements MessageFilterObserver {

    private ObjectInputStream objectInputStream;
    private List<MessageFilter> messageFilterList;
    private ObjectMapper objectMapper;

    private boolean isListening = true;

    public ListenerThread(InputStream inputStream) throws IOException {
        System.out.println("Listener started...");
        objectInputStream = new ObjectInputStream(inputStream);
        messageFilterList = new ArrayList<>();
        objectMapper = new ObjectMapper();
    }

    @Override
    public void run() {
        try {
            waitForMessage();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    private void waitForMessage() throws IOException {
        Message message;
        String jsonMessage = null;
        while (true) {

            try {
                Thread.sleep(500);
                jsonMessage = (String) objectInputStream.readObject();
            } catch (Exception e) {
                e.printStackTrace();
            }

            message = objectMapper.readValue(jsonMessage, Message.class);
            if (isNotBlank(jsonMessage)) {
                displayMessage(message);
            }

            if (!isListening) {
                break;
            }
        }
        objectInputStream.close();
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

}
