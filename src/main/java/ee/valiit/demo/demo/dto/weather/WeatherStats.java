package ee.valiit.demo.demo.dto.weather;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherStats {

    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd-MM-yyyy' 'HH:mm:ss")
    @JsonProperty("Time")
    private LocalDateTime dateTime;
    @JsonProperty("Temperature")
    private Double temp;
    @JsonProperty("Conditions")
    private String description;
    @JsonProperty("Wind speed")
    private Double windSpeed;
    @JsonProperty("Humidity")
    private Integer humidity;
}
