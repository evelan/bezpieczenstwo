package pl.pwr.client;

import java.io.IOException;

/**
 * Created by Evelan on 12/10/2016.
 */
public class ClientStart {

    public static void main(String[] args) {
        Client client = new Client();
        try {
            client.invoke();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
