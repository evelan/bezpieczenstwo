package pl.pwr.common.service.encryption.none;

import pl.pwr.common.service.encryption.EncryptionProvider;

import java.math.BigInteger;

/**
 * Created by Evelan on 24/10/2016.
 */
public class NoneEncryption implements EncryptionProvider {
    @Override
    public String decrypt(String encryptedMessage, BigInteger key) {
        return encryptedMessage;
    }

    @Override
    public String encrypt(String plainText, BigInteger key) {
        return plainText;
    }
}
