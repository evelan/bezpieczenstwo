package pl.pwr.common.connection.sender;

import pl.pwr.common.model.EncryptionType;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;

/**
 * Created by Evelan on 15/10/2016.
 */
public class SenderImpl implements Sender {

    private ObjectOutputStream objectOutputStream;


    public SenderImpl(Socket socket) {
        try {
            objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void sendMessage(String message) {
        sendJSON(message);
    }

    @Override
    public void sendEncryptionType(EncryptionType encryptionType) {
        sendJSON(encryptionType.name());
    }

    @Override
    public void sendSecretKey(String secretKey) {
        sendJSON(secretKey);
    }

    @Override
    public void sendPublicKeys(String p, String q) {
        sendJSON(p+q);
    }

    private void sendJSON(String json) {
        try {
            objectOutputStream.writeObject(json);
            objectOutputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
