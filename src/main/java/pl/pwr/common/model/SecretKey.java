package pl.pwr.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Evelan on 18/10/2016.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class SecretKey {

    @JsonProperty("a")
    String key;

}
