package ee.valiit.demo.demo.connector;
import ee.valiit.demo.demo.dto.weather.WeatherDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
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
        log.info("kuku {}", api);
        ResponseEntity<WeatherDto> getWeather = restTemplate.exchange(api+"weather?q={city}&appid={appId}&units={units}", HttpMethod.GET, null, WeatherDto.class, city, appId, units);
        return getWeather.getBody();
    }

    //api.openweathermap.org/data/2.5/weather?q=tallinn&appid=eeb6c98a4a9912f4e273e67ac847a9ed&units=metric
}
