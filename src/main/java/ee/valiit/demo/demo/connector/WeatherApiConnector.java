package ee.valiit.demo.demo.connector;

import ee.valiit.demo.demo.dto.weather.WeatherDto;
import ee.valiit.demo.demo.exception.Error;
import ee.valiit.demo.demo.exception.GeneralException;
import ee.valiit.demo.demo.exception.NotFoundException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;

@Slf4j
@Service
public class WeatherApiConnector {

    @Autowired
    private RestTemplate restTemplate;

    @Value("${api}")
    private String api;

    @Value("${appId}")
    private String appId;
    @Value("${units}")
    private String units;

    public WeatherDto getWeather(String city) {
        try {
            ResponseEntity<WeatherDto> getWeather = restTemplate.exchange(api + "weather?q={city}&appid={appId}&units={units}", HttpMethod.GET, null, WeatherDto.class, city, appId, units);
            return getWeather.getBody();
        } catch (HttpStatusCodeException he) {
            log.error("WeatherApiConnector: weather info for city {} not found, code {}", city, he.getStatusCode());
            throw new NotFoundException("Weather info for city not found", Error.Code.NOT_FOUND);
        } catch (Exception e) {
            log.error("WeatherApiConnector: something else went wrong", e);
            throw new GeneralException("API not responding", Error.Code.NO_RESPONSE);
        }
    }

    //api.openweathermap.org/data/2.5/weather?q=tallinn&appid=eeb6c98a4a9912f4e273e67ac847a9ed&units=metric
}
