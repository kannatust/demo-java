package ee.valiit.demo.demo.dto.country;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class BaseCountry {
    private String name;
    private String capital;
    private Integer population;
    private String region;

}
