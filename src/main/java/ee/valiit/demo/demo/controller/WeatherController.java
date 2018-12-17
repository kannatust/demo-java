package ee.valiit.demo.demo.controller;

import ee.valiit.demo.demo.dto.weather.WeatherDto;
import ee.valiit.demo.demo.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


@Slf4j
@RestController
public class WeatherController {

    @Autowired
    WeatherService weatherService;



    @RequestMapping(value = "/weather/{city}", method = RequestMethod.GET)
    public WeatherDto getWeather(@PathVariable String city){
        log.info("kas kood jouab siia {}", city);
        return weatherService.getWeather(city);

    }
}
