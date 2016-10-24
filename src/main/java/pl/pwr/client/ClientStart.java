package pl.pwr.client;

/**
 * Created by Evelan on 12/10/2016.
 */
public class ClientStart {

    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.invoke();
        } catch (Exception e) {
            System.err.println("Something go REALLY wrong: " + e.getMessage());
        }
    }
}
