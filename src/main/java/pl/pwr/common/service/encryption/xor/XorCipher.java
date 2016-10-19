package pl.pwr.common.service.encryption.xor;

import pl.pwr.common.service.encryption.Decryption;
import pl.pwr.common.service.encryption.Encryption;
import sun.reflect.generics.reflectiveObjects.NotImplementedException;

/**
 * Created by Evelan on 17/10/2016.
 */
public class XorCipher implements Encryption, Decryption {


    @Override
    public String decrypt(String encryptedMessage, Integer key) {
        throw new NotImplementedException();
    }

    @Override
    public String encrypt(String plainText, Integer key) {
        throw new NotImplementedException();
    }
}
