package pl.pwr.common.service.encryption.xor;

import pl.pwr.common.service.encryption.EncryptionProvider;

import java.math.BigInteger;

/**
 * Created by Evelan on 17/10/2016.
 */
public class XorCipher implements EncryptionProvider {

    @Override
    public String decrypt(String encryptedMessage, BigInteger key) {
        return new String(xorMessageWithKey(encryptedMessage, key));
    }

    @Override
    public String encrypt(String plainText, BigInteger key) {
        return new String(xorMessageWithKey(plainText, key));
    }

    /**
     * Xor Encyrption
     *
     * @param message - plain or encrypted message
     * @param key     - key
     * @return encrypted or plain message
     */
    private byte[] xorMessageWithKey(String message, BigInteger key) {
        byte[] messageBytes = message.getBytes();
        byte[] outputMessage = messageBytes.clone();
        byte[] keyBytes = key.toByteArray();
        byte lastKeyByte = keyBytes[keyBytes.length - 1];

        for (int i = 0; i < messageBytes.length; i++) {
            byte xorMessageAndKey = (byte) (messageBytes[i] ^ lastKeyByte);
            outputMessage[i] = xorMessageAndKey;
        }
        return outputMessage;
    }

}
