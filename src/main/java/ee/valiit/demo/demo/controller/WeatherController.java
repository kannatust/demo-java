package ee.valiit.demo.demo.controller;

import ee.valiit.demo.demo.dto.city.CityDto;
import ee.valiit.demo.demo.dto.weather.WeatherDto;
import ee.valiit.demo.demo.dto.weather.WeatherStats;
import ee.valiit.demo.demo.model.city.City;
import ee.valiit.demo.demo.model.weather.Weather;
import ee.valiit.demo.demo.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;


@Slf4j
@RestController
public class WeatherController {

    @Autowired
    WeatherService weatherService;



/*
   @RequestMapping(value = "/weather/{city}", method = RequestMethod.GET)
    public WeatherDto getWeather(@PathVariable String city){
        log.info("kas kood jouab siia {}", city);
        return weatherService.getWeather(city);
*/


    @RequestMapping(value = "/weather/{city}", method = RequestMethod.GET)
    public List<WeatherStats> getStats(@PathVariable String city) {
        log.info("kas kood jouab siia {}", city);
        return weatherService.getStats(city);

    }

    /*
    Välja kommenteeritud, kuna @scheduled teeb automaatselt sama asja
    @RequestMapping(value = "/weather/{city}", method = RequestMethod.POST)
    public void saveWeather(@PathVariable String city) {
        weatherService.saveWeather(city);
    }*/
}

