package ee.valiit.demo.demo.dto.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDto {

    private List<WeatherData> weather;
    @JsonProperty("main")
    private Temps temps;
    private Wind wind;
    private List<WeatherStats> weatherStats;
    }