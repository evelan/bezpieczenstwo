package pl.pwr.common.model;

/**
 * Created by Evelan on 12/10/2016.
 */
public enum EncryptionType {
    NONE("none"), CEZAR("cezar"), XOR("xor");

    String value;

    EncryptionType(String value) {
        this.value = value;
    }

}
