package ee.valiit.demo.demo.service;

import ee.valiit.demo.demo.dto.city.CityDto;
import ee.valiit.demo.demo.model.city.City;
import ee.valiit.demo.demo.repository.CityRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Slf4j
@Service
public class CityService {

    @Autowired
    CityRepository cityRepository;

    @Autowired
    WeatherService weatherService;


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
