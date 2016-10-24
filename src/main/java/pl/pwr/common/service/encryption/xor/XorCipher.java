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

    private byte[] xorMessageWithKey(String message, BigInteger key) {
        byte[] messageBytes = message.getBytes();
        byte[] outputMessage = new byte[messageBytes.length];
        byte[] keyBytes = intToByteArray(key.intValue());
        for (int i = 0; i < messageBytes.length; i++) {
            byte xorMessageAndKey = (byte) (messageBytes[i] ^ keyBytes[keyBytes.length - 1]);
            outputMessage[i] = xorMessageAndKey;
        }
        return outputMessage;
    }

    private byte[] intToByteArray(int value) {
        return new byte[]{
                (byte) (value >>> 24),
                (byte) (value >>> 16),
                (byte) (value >>> 8),
                (byte) value};
    }

}
