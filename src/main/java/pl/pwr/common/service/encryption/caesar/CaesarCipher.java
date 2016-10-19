package pl.pwr.common.service.encryption.caesar;

import pl.pwr.common.service.encryption.Decryption;
import pl.pwr.common.service.encryption.Encryption;
import pl.pwr.common.service.encryption.caesar.operation.Operation;
import pl.pwr.common.service.encryption.caesar.operation.OperationAdd;
import pl.pwr.common.service.encryption.caesar.operation.OperationContext;
import pl.pwr.common.service.encryption.caesar.operation.OperationSubstract;

/**
 * Created by Evelan on 17/10/2016.
 */
public class CaesarCipher implements Encryption, Decryption {


    @Override
    public String decrypt(String encryptedMessage, Integer key) {
        return crypt(encryptedMessage, key, new OperationSubstract());
    }

    @Override
    public String encrypt(String plainText, Integer key) {
        return crypt(plainText, key, new OperationAdd());
    }

    private String crypt(String text, Integer key, Operation operation) {
        OperationContext operationContext = new OperationContext(operation);
        key %= 26;
        char[] chars = text.toCharArray();
        for (int i = 0; i < chars.length; i++) {
            chars[i] -= operationContext.executeOperation(chars[i], key);
        }
        return String.valueOf(chars);
    }
}
