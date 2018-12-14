package ee.valiit.demo.demo.model;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import ee.valiit.demo.demo.dto.Clouds;
import ee.valiit.demo.demo.dto.Coord;
import ee.valiit.demo.demo.dto.Temps;
import ee.valiit.demo.demo.dto.Wind;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class Weather {
    private Coord coord;
    private Weather weather;
    private String base;
    @JsonProperty("main")
    private Temps temps;
    private Integer visibility;
    private Wind wind;
    private Clouds clouds;
    private String id;
    private String name;
    private String city;

}
