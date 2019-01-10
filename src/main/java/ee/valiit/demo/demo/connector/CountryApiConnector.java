package ee.valiit.demo.demo.connector;

import ee.valiit.demo.demo.dto.country.BaseCountry;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Slf4j
@Service
public class CountryApiConnector {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api2}")
    private String api2;

    public List<BaseCountry> getCity(String country) {
        ResponseEntity<List<BaseCountry>> getCity = restTemplate.exchange(api2+"{country}", HttpMethod.GET, null, new ParameterizedTypeReference<List<BaseCountry>>() {
        }, country);
        return getCity.getBody();
    }

}
