package ee.valiit.demo.demo.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ee.valiit.demo.demo.model.Weather;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class WeatherDto {

    private List<WeatherData> weather;
    @JsonProperty("main")
    private Temps temps;
    private Integer visibility;
    private Wind wind;
    private Clouds clouds;
    private String name;
    private String city;
}
