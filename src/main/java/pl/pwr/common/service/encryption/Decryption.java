package pl.pwr.common.service.encryption;

/**
 * Created by Evelan on 17/10/2016.
 */
public interface Decryption {
    String decrypt(String encryptedMessage, Integer key);
}
