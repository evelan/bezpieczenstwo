package pl.pwr.common.service.interaction;

/**
 * Created by Evelan on 15/10/2016.
 */
public interface UserInteractionService {

    String getInputFromUser();

    void displayMessage(String from, String text);

}
