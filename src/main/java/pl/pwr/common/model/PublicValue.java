package pl.pwr.common.model;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

/**
 * Created by Evelan on 12/10/2016.
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class PublicValue {

    @JsonProperty("p")
    String p;

    @JsonProperty("g")
    String g;

}
