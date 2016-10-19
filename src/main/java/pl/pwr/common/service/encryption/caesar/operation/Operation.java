package pl.pwr.common.service.encryption.caesar.operation;

/**
 * Created by Evelan on 19/10/2016.
 */
public interface Operation {

    char doOperation(char singleChar, Integer key);
}
