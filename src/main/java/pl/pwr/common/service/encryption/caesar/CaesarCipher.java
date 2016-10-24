package pl.pwr.common.service.encryption.caesar;

import pl.pwr.common.service.encryption.EncryptionProvider;
import pl.pwr.common.service.encryption.caesar.operation.Operation;
import pl.pwr.common.service.encryption.caesar.operation.OperationAdd;
import pl.pwr.common.service.encryption.caesar.operation.OperationContext;
import pl.pwr.common.service.encryption.caesar.operation.OperationSubstract;

import java.math.BigInteger;

/**
 * Created by Evelan on 17/10/2016.
 */
public class CaesarCipher implements EncryptionProvider {

    @Override
    public String decrypt(String encryptedMessage, BigInteger key) {
        return crypt(encryptedMessage, key, new OperationSubstract());
    }

    @Override
    public String encrypt(String plainText, BigInteger key) {
        return crypt(plainText, key, new OperationAdd());
    }

    private String crypt(String text, BigInteger key, Operation operation) {
        OperationContext operationContext = new OperationContext(operation);
        key = key.mod(BigInteger.valueOf(26));
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] = operationContext.executeOperation(chars[i], key);
        }
        return String.valueOf(chars);
    }
}
