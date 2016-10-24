package pl.pwr.common.service.encryption;

import pl.pwr.common.model.EncryptionType;
import pl.pwr.common.service.encryption.caesar.CaesarCipher;
import pl.pwr.common.service.encryption.none.NoneEncryption;
import pl.pwr.common.service.encryption.xor.XorCipher;

import static pl.pwr.common.model.EncryptionType.CAESAR;
import static pl.pwr.common.model.EncryptionType.XOR;

/**
 * Created by Evelan on 24/10/2016.
 */
public class EncryptionFactory {

    public static EncryptionProvider getProvider(EncryptionType encryptionType) {
        if (encryptionType.equals(CAESAR)) {
            return new CaesarCipher();
        } else if (encryptionType.equals(XOR)) {
            return new XorCipher();
        } else {
            return new NoneEncryption();
        }
    }

}
