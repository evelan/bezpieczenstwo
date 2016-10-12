package pl.pwr.common.model;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Evelan on 12/10/2016.
 */
@Getter
@Setter
public class Message {

    @JsonProperty("msg")
    String msg;

    @JsonProperty("from")
    String from;
}
