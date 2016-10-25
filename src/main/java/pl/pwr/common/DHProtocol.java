package pl.pwr.common;

import java.math.BigInteger;
import java.util.Random;

/**
 * Created by Evelan on 24/10/2016.
 */
public class DHProtocol {

    public static BigInteger calculateSecret(BigInteger willBePowered, BigInteger power, BigInteger modulo) {
        BigInteger poweredValue = willBePowered.pow(power.intValue());
        return poweredValue.mod(modulo);
    }

    public static BigInteger generatePrime() {
        return BigInteger.probablePrime(8, new Random());
    }


}
