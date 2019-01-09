package ee.valiit.demo.demo.controller;

import ee.valiit.demo.demo.dto.weather.WeatherByDate;
import ee.valiit.demo.demo.dto.weather.WeatherStats;
import ee.valiit.demo.demo.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@Slf4j
@RestController
public class WeatherController {

    @Autowired
    WeatherService weatherService;


    @RequestMapping(value = "/weather/{city:[\\D]}", method = RequestMethod.GET)
    public List<WeatherStats> getStats(@PathVariable String city) {
        log.info("kas kood jouab siia {}", city);
        return weatherService.getStats(city);

    }

    @RequestMapping(value = "/weather/{dateInput}/{isMax}", method = RequestMethod.GET)
    public List<WeatherByDate> getTempsByDate(@PathVariable String dateInput, @PathVariable Boolean isMax){
        if (isMax) return weatherService.getMaxTemps(dateInput);

        else return weatherService.getMinTemps(dateInput);
        }

}


