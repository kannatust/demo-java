package ee.valiit.demo.demo.dto.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherByDate {

    @JsonProperty("City")
    private String name;
    @JsonProperty("Temperature")
    private Double temp;
}
