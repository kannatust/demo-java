package ee.valiit.demo.demo.service;

import ee.valiit.demo.demo.connector.CountryApiConnector;
import ee.valiit.demo.demo.dto.city.CityDto;
import ee.valiit.demo.demo.dto.country.BaseCountry;
import ee.valiit.demo.demo.dto.country.CountryDto;
import ee.valiit.demo.demo.dto.weather.Temps;
import ee.valiit.demo.demo.dto.weather.WeatherDto;
import ee.valiit.demo.demo.exception.Error;
import ee.valiit.demo.demo.exception.ValidationException;
import ee.valiit.demo.demo.model.city.City;
import ee.valiit.demo.demo.repository.CityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class CityService {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    WeatherService weatherService;

    @Autowired
    CountryApiConnector countryApiConnector;

    public CountryDto getCountryData(String country) {
        List<BaseCountry> countryData = countryApiConnector.getCity(country);
        BaseCountry region = new BaseCountry();
        log.info("countryData = {}", countryData);
        CountryDto countryDataWithTemp = new CountryDto();
            for (BaseCountry source : countryData) {
                countryDataWithTemp.setName(source.getName());
                countryDataWithTemp.setCapital(source.getCapital());
                countryDataWithTemp.setPopulation(source.getPopulation());
                region.setRegion(source.getRegion());
            }
            if (region.getRegion().equals("Europe")) {
                WeatherDto weatherForCity = weatherService.getWeather(countryDataWithTemp.getCapital());
                Temps temps = weatherForCity.getTemps();
                countryDataWithTemp.setTemp(temps.getTemp());
            }
            return countryDataWithTemp;
    }

    public City addCity(CityDto cityDto) {
        City cityInput = cityRepository.findByName(cityDto.getName());
        log.info("cityInput = {}", cityInput);

        if (cityInput != null) {
            throw new ValidationException("City already exists", Error.Code.ALREADY_EXISTS);
        }

        City city = new City();
        city.setName(weatherService.cityTemporary(cityDto.getName()));
        City newCity = cityRepository.save(city);

        return newCity;
     }

    public void deleteCity(String name) {
        City cityFound = cityRepository.findByName(weatherService.cityTemporary(name));
        log.info("kas linn leiti? {}", cityFound);

        if (cityFound != null) {
            City city = new City();
            city.setId(cityFound.getId());
            city.setName(weatherService.cityTemporary(name));
            log.info("Kas citysse konverditi? {}", city);
            cityRepository.delete(city);
        }
        else {
            throw new ValidationException("No such city in database", Error.Code.NOT_FOUND);
        }
    }
}
