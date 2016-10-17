package pl.pwr.common.connection.sender;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.pwr.common.model.EncryptionType;
import pl.pwr.common.model.Message;
import pl.pwr.common.model.PublicValue;
import pl.pwr.common.model.Request;

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
    public void sendSecretKey(String secretKey) {
        sendJSON(secretKey);
    }

    @Override
    public void sendPublicKeys(String p, String g) {
        PublicValue publicValue = new PublicValue(p, g);
        sendJSON(publicValue);
    }

    @Override
    public void sendKeysRequest() {
        Request request = new Request("keys");
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
