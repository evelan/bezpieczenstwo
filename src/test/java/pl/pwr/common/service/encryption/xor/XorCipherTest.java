package pl.pwr.common.service.encryption.xor;

import org.junit.Test;

import java.math.BigInteger;

/**
 * Created by Evelan on 24/10/2016.
 */
public class XorCipherTest {
    @Test
    public void decrypt() throws Exception {


    }

    @Test
    public void encrypt() throws Exception {

        BigInteger key = BigInteger.valueOf(12);
        String message = "abc";
        XorCipher xorCipher = new XorCipher();
        String encryptedMessage = xorCipher.encrypt(message, key);


    }

}