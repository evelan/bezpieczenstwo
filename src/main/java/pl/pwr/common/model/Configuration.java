package pl.pwr.common.model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Created by Evelan on 15/10/2016.
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Configuration {
    String portNumber;
    String host;
}
