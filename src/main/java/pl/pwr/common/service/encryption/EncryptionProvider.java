package pl.pwr.common.service.encryption;

import java.math.BigInteger;

/**
 * Created by Evelan on 17/10/2016.
 */
public interface EncryptionProvider {
    String decrypt(String encryptedMessage, BigInteger key);

    String encrypt(String plainText, BigInteger key);
}