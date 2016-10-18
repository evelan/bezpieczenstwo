package pl.pwr.common.connection.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.pwr.common.model.*;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.OutputStream;

/**
 * Created by Evelan on 15/10/2016.
 */
public class SenderImpl implements Sender {

    private ObjectOutputStream objectOutputStream;
    private ObjectMapper objectMapper;

    public SenderImpl(OutputStream outputStream) {
        try {
            objectOutputStream = new ObjectOutputStream(outputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        objectMapper = new ObjectMapper();
    }

    @Override
    public void sendMessage(String plainMessage) {
        Message message = new Message(plainMessage, "anonymous");
        sendJSON(message);
    }

    @Override
    public void sendEncryptionType(EncryptionType encryptionType) {
        pl.pwr.common.model.Encryption encryption = new pl.pwr.common.model.Encryption(encryptionType);
        sendJSON(encryption);
    }

    @Override
    public void sendSecretKey(String key) {
        SecretKey secretKey = new SecretKey(key);
        sendJSON(secretKey);
    }

    @Override
    public void sendPublicKeys(String p, String g) {
        PublicKey publicKey = new PublicKey(p, g);
        sendJSON(publicKey);
    }

    @Override
    public void sendKeysRequest() {
        KeyRequest request = new KeyRequest("keys");
        sendJSON(request);
    }

    private void sendJSON(Object object) {
        try {
            String json = objectMapper.writeValueAsString(object);
            objectOutputStream.writeObject(json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
