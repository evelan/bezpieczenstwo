package pl.pwr.common.service.interaction;

import pl.pwr.common.service.filter.MessageFilter;
import pl.pwr.common.service.filter.MessageFilterObserver;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * Created by Evelan on 15/10/2016.
 */
public class UserInteractionServiceImpl implements UserInteractionService, MessageFilterObserver {

    private Scanner scanner;
    private List<MessageFilter> messageFilterList;

    public UserInteractionServiceImpl() {
        scanner = new Scanner(System.in);
        messageFilterList = new ArrayList<>();
    }

    @Override
    public String getInputFromUser() {
        return scanner.next();
    }

    public void displayMessage(String from, String text) {

        for (MessageFilter messageFilter : messageFilterList) {
            text = messageFilter.doFilter(text);
        }

        System.out.println(from + " says: " + text);
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
