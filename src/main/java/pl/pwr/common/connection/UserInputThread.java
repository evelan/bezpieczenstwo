package pl.pwr.common.connection;

import pl.pwr.common.connection.sender.Sender;

import java.util.Scanner;

/**
 * Created by Evelan on 15/10/2016.
 */
public class UserInputThread extends Thread {

    private Scanner scanner;
    private Sender sender;
    private boolean isInteracting;

    public UserInputThread(Sender sender) {
        scanner = new Scanner(System.in);
        this.sender = sender;
        isInteracting = true;
    }

    @Override
    public void run() {
        while (isInteracting) {
            try {
                String plainMessage = scanner.next();
                sender.sendMessage(plainMessage);
                System.out.println("Message sent: " + plainMessage);
            }catch (Exception e){
                e.printStackTrace();
                stopInteracting();
            }
        }
    }

    public void stopInteracting() {
        isInteracting = false;
    }
}
