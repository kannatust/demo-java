package ee.valiit.demo.demo.controller;

import ee.valiit.demo.demo.dto.weather.WeatherByDate;
import ee.valiit.demo.demo.dto.weather.WeatherStats;
import ee.valiit.demo.demo.service.WeatherService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;


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

    @RequestMapping(value = "/weather/{dateInput}/{minOrMax}", method = RequestMethod.GET)
    public List<WeatherByDate> getTempsByDate(@PathVariable String dateInput, @PathVariable Boolean isMax){
        /*if (isMax == true) {
            List<WeatherByDate> all = weatherService.getTempsByDate(dateInput);
            for (WeatherByDate source : all ){

                all.stream()
                        .max(Comparator.comparing(WeatherByDate::getTemp))
                        .orElseThrow(NoSuchElementException::new);
                List<WeatherByDate> maxByTemp = new ArrayList<>();
                maxByTemp.add()
            }

            WeatherByDate maxByTemp = weatherService.getTempsByDate(dateInput)
            return weatherService.getTempsByDate(dateInput).stream()
                    .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
                    .entrySet().stream().filter(x -> x.getValue() == 1 )
                    .map(x -> x.getKey())
                    .collect(Collectors.toList());
         }
*/
        return weatherService.getTempsByDate(dateInput);
    }

}

