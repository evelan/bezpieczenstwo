package pl.pwr.common.service.encryption.xor;

import org.junit.Assert;
import org.junit.Test;

import java.math.BigInteger;

/**
 * Created by Evelan on 24/10/2016.
 */
public class XorCipherTest {

    @Test
    public void encryptAndDecrypt_correct() throws Exception {

        BigInteger key = BigInteger.valueOf(12);
        String message = "Jakub Pomykała";
        XorCipher xorCipher = new XorCipher();
        String encryptedMessage = xorCipher.encrypt(message, key);

        String result = xorCipher.decrypt(encryptedMessage, key);

        Assert.assertEquals(message, result);

    }

}