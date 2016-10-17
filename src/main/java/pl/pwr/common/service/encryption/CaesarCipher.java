package pl.pwr.common.service.encryption;

import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Evelan on 17/10/2016.
 */
public class CaesarCipher implements Encryption, Decryption {

    @Override
    public String decrypt(String encryptedMessage, String key) {
        throw new NotImplementedException();
    }

    @Override
    public String encrypt(String plainText, String key) {
        throw new NotImplementedException();
    }
}
