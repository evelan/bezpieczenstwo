package pl.pwr.common.connection.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.pwr.common.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created by Evelan on 18/10/2016.
 */
public class ListenerImpl implements Listener {

    private ObjectInputStream stream;
    private ObjectMapper objectMapper;

    public ListenerImpl(InputStream inputStream) {
        try {
            stream = new ObjectInputStream(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        objectMapper = new ObjectMapper();
    }

    @Override
    public Message waitForMessage() throws IOException {
        String json = waitForJson();
        return objectMapper.readValue(json, Message.class);
    }

    @Override
    public Encryption waitForEncryptionType() throws IOException {
        String json = waitForJson();
        return objectMapper.readValue(json, Encryption.class);
    }

    @Override
    public SecretKey waitForSecretKey() throws IOException {
        String json = waitForJson();
        return objectMapper.readValue(json, SecretKey.class);
    }

    @Override
    public PublicKey waitForPublicKeys() throws IOException {
        String json = waitForJson();
        return objectMapper.readValue(json, PublicKey.class);
    }

    @Override
    public KeyRequest waitForKeysRequest() throws IOException {
        String json = waitForJson();
        return objectMapper.readValue(json, KeyRequest.class);
    }

    @Override
    public Object waitFor(Class<?> clazz) throws IOException {
        String json = waitForJson();
        return objectMapper.readValue(json, clazz);
    }

    private String waitForJson() {
        String output;
        do {
            output = readFromStream();
        } while (isBlank(output));
        return output;
    }

    private String readFromStream() {
        String output;
        try {
            output = (String) stream.readObject();
        } catch (Exception e) {
            output = "";
            System.err.println("Error: " + e.getMessage());
        }
        return output;
    }
}
