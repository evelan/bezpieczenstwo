package pl.pwr.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import pl.pwr.common.service.encryption.EncryptionProvider;
import pl.pwr.common.service.encryption.none.NoneEncryption;

import java.math.BigInteger;

/**
 * Created by Evelan on 24/10/2016.
 */
@Data
@AllArgsConstructor
@Builder
public class Authorization {
    EncryptionProvider encryptionProvider;
    BigInteger key;

    public Authorization() {
        encryptionProvider = new NoneEncryption();
        key = BigInteger.ONE;
    }
}
