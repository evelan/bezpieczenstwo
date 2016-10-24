package pl.pwr.common.service.encryption;

import org.junit.Assert;
import org.junit.Test;
import pl.pwr.common.service.encryption.caesar.CaesarCipher;

import java.math.BigInteger;

/**
 * Created by Evelan on 19/10/2016.
 */
public class CaesarCipherTest {
    @Test
    public void testDecryptROT3() throws Exception {
        BigInteger key = BigInteger.valueOf(3);
        String encryptedText = "mdnxe";

        CaesarCipher caesarCipher = new CaesarCipher();
        String decryptedText = caesarCipher.decrypt(encryptedText, key);

        String expectedDecryptedText = "jakub";
        Assert.assertEquals(expectedDecryptedText, decryptedText);
    }

    @Test
    public void testEncryptROT3() throws Exception {
        BigInteger key = BigInteger.valueOf(3);
        String plainText = "jakub";

        CaesarCipher caesarCipher = new CaesarCipher();
        String encryptedROT3 = caesarCipher.encrypt(plainText, key);

        String expectedEncryptedText = "mdnxe";
        Assert.assertEquals(expectedEncryptedText, encryptedROT3);
    }


    @Test
    public void testEncryptROTBiggerThan26() throws Exception {
        BigInteger key = BigInteger.valueOf(54);
        String plainText = "jhsjASSDASDASfasds";

        CaesarCipher caesarCipher = new CaesarCipher();
        String encrypted = caesarCipher.encrypt(plainText, key);

        String expectedEncryptedText = "ljulCUUFCUFCUhcufu";
        Assert.assertEquals(expectedEncryptedText, encrypted);
    }
}