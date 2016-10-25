package pl.pwr.common.connection.listener;

import com.fasterxml.jackson.databind.ObjectMapper;
import pl.pwr.common.model.*;

import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.util.Base64;

import static org.apache.commons.lang3.StringUtils.isBlank;

/**
 * Created by Evelan on 18/10/2016.
 */
public class ListenerImpl implements Listener {

    private ObjectInputStream stream;
    private ObjectMapper objectMapper;

    public ListenerImpl(InputStream inputStream) throws IOException {
        stream = new ObjectInputStream(inputStream);
        objectMapper = new ObjectMapper();
    }

    @Override
    public Message waitForMessage() throws IOException {
        String json = waitForJson();
        return objectMapper.readValue(json, Message.class);
    }

    @Override
    public EncryptionType waitForEncryptionType() throws IOException {
        String json = waitForJson();
        Encryption encryption = objectMapper.readValue(json, Encryption.class);
        return encryption.getEncryption();
    }

    @Override
    public SecretKey waitForSecretKey() {
        try {
            String json = waitForJson();
            return objectMapper.readValue(json, SecretKey.class);
        } catch (IOException ignored) {
        }
        return new SecretKey();
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

    private String waitForJson() throws IOException {
        String output = "";
        do {
            try {
                output = readFromStream();
            } catch (Exception e) {
                System.err.println("Connection ended by remote client/server");
                break;
            }
        } while (isBlank(output));
        return output;
    }

    /**
     * Reading from stream and decoding from Base64
     *
     * @return plain JSON string
     */
    private String readFromStream() throws IOException, ClassNotFoundException {
        String output;
        byte[] outputBytes;
        outputBytes = (byte[]) stream.readObject();
        byte[] base64decodedBytes = Base64.getDecoder().decode(outputBytes);
        output = new String(base64decodedBytes, "utf-8");
        return output;
    }
}
