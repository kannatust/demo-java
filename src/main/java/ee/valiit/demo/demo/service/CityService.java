package ee.valiit.demo.demo.service;

import ee.valiit.demo.demo.connector.CountryApiConnector;
import ee.valiit.demo.demo.dto.city.CityDto;
import ee.valiit.demo.demo.dto.country.BaseCountry;
import ee.valiit.demo.demo.dto.country.CountryDto;
import ee.valiit.demo.demo.dto.weather.Temps;
import ee.valiit.demo.demo.dto.weather.WeatherDto;
import ee.valiit.demo.demo.model.city.City;
import ee.valiit.demo.demo.repository.CityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        CountryDto countryDataWithTemp = new CountryDto();
        for (BaseCountry source : countryData) {
            countryDataWithTemp.setName(source.getName());
            countryDataWithTemp.setCapital(source.getCapital());
            countryDataWithTemp.setPopulation(source.getPopulation());
        }
        WeatherDto weatherForCity = weatherService.getWeather(countryDataWithTemp.getCapital());
        Temps temps = weatherForCity.getTemps();
        countryDataWithTemp.setTemp(temps.getTemp());
        return countryDataWithTemp;
    }

    public void addCity(CityDto cityDto) {
        Optional<City> cityFound = cityRepository.findByName(cityDto.getName());
        if (!cityFound.isPresent()) {
            City city = new City();
            city.setName(weatherService.cityTemp(cityDto.getName()));
            cityRepository.save(city);
        }
     }

    public void deleteCity(String name) {
        Optional<City> cityFound = cityRepository.findByName(weatherService.cityTemp(name));
        log.info("kas linn leiti? {}", cityFound);
        City city = new City();
        city.setId(cityFound.get().getId());
        city.setName(weatherService.cityTemp(name));
        log.info("Kas citysse konverditi? {}", city);
        cityRepository.delete(city);
    }
}
