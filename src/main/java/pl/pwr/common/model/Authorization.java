package pl.pwr.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import pl.pwr.common.service.encryption.EncryptionProvider;

import java.math.BigInteger;

/**
 * Created by Evelan on 24/10/2016.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Authorization {
    EncryptionProvider encryptionProvider;
    BigInteger key;
}
