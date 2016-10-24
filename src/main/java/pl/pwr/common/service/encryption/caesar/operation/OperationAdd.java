package pl.pwr.common.service.encryption.caesar.operation;

import java.math.BigInteger;

/**
 * Created by Evelan on 19/10/2016.
 */
public class OperationAdd implements Operation {

    @Override
    public char doOperation(char singleChar, BigInteger key) {
        return (char) (key.intValue() + singleChar);
    }
}
