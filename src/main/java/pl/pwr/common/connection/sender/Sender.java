package pl.pwr.common.connection.sender;

import pl.pwr.common.model.EncryptionType;

/**
 * Created by Evelan on 15/10/2016.
 */
public interface Sender {

    void sendMessage(String message, String from);

    void sendMessage(String plainMessage);

    void sendEncryptionType(EncryptionType encryptionType);

    void sendSecretKey(String secretKey);

    void sendPublicKeys(String p, String q);

    void sendKeysRequest();
}
