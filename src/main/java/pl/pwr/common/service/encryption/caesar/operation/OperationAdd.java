package pl.pwr.common.service.encryption.caesar.operation;

/**
 * Created by Evelan on 19/10/2016.
 */
public class OperationAdd implements Operation {

    @Override
    public char doOperation(char singleChar, Integer key) {
        return (char) (singleChar + key);
    }
}
