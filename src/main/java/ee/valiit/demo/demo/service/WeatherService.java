package ee.valiit.demo.demo.service;


import ee.valiit.demo.demo.connector.WeatherApiConnector;
import ee.valiit.demo.demo.dto.WeatherDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;


@Slf4j
@Service
public class WeatherService {

    @Autowired
    WeatherApiConnector weatherApiConnector;



    public WeatherDto getWeather(String city) {
    return weatherApiConnector.getWeather(city);
    }

}
