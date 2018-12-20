package ee.valiit.demo.demo.controller;

import ee.valiit.demo.demo.dto.city.CityDto;
import ee.valiit.demo.demo.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class CityController {

    @Autowired
    CityService cityService;

    @Autowired
    WeatherController weatherController;

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public void addCity(@RequestBody CityDto item){
        cityService.addCity(item);

    }


    @RequestMapping(value = "/city/{Name}", method = RequestMethod.DELETE)
    public void deleteCity(@PathVariable String Name){
        cityService.deleteCity(Name.toLowerCase());

    }

}
