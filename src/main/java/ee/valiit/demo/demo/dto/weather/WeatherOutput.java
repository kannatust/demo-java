package ee.valiit.demo.demo.dto.weather;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherOutput {
    private List<WeatherStats> weatherOutput;

}
