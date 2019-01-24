package ee.valiit.demo.demo.controller;

import ee.valiit.demo.demo.dto.country.CountryDto;
import ee.valiit.demo.demo.service.CityService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
public class CountryController {

    @Autowired
    CityService cityService;

    @RequestMapping(value = "/country/{country}", method = RequestMethod.GET)
    public CountryDto getCity(@PathVariable String country) {
        return cityService.getCountryData(country);
    }
}

