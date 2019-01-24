package ee.valiit.demo.demo.connector;

import ee.valiit.demo.demo.dto.country.BaseCountry;
import ee.valiit.demo.demo.exception.Error;
import ee.valiit.demo.demo.exception.GeneralException;
import ee.valiit.demo.demo.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
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
        try {
            ResponseEntity<List<BaseCountry>> getCity = restTemplate.exchange(api2 + "{country}", HttpMethod.GET, null, new ParameterizedTypeReference<List<BaseCountry>>() {
            }, country);
                return getCity.getBody();
            // HttpStatusCodeException - APIlt tuleb mingi vastus (kood)
        }   catch (HttpStatusCodeException he) {
            log.error("CountryApiConnector: country not found, code {}", he.getStatusCode());
            throw new NotFoundException("Country not found", Error.Code.NOT_FOUND);
            // Exception - kui serverilt üldse mingit koodi vastuseks ei tule
        } catch (Exception e) {
            log.error("CountryApiConnector: something else went wrong", e);
            throw new GeneralException("API not responding", Error.Code.NO_RESPONSE);
        }
    }
}
