package ee.valiit.demo.demo.dto.country;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CountryDto {
    @JsonProperty("country")
    private String name;
    @JsonProperty("population")
    private Integer population;
    @JsonProperty("capital")
    private String capital;
    @JsonProperty("current temp in capital")
    private Double temp;


}
