package ee.valiit.demo.demo.dto.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Temps {
    Double temp;
    //Integer pressure;
    Integer humidity;
    //Integer tempMin;
    //Integer tempMax;
}
