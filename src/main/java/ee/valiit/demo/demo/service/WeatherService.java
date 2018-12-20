package ee.valiit.demo.demo.service;


import ee.valiit.demo.demo.connector.WeatherApiConnector;
import ee.valiit.demo.demo.dto.weather.*;
import ee.valiit.demo.demo.model.city.City;
import ee.valiit.demo.demo.model.weather.Weather;
import ee.valiit.demo.demo.repository.CityRepository;
import ee.valiit.demo.demo.repository.WeatherRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Slf4j
@Service
public class WeatherService {

    @Autowired
    WeatherApiConnector weatherApiConnector;

    @Autowired
    WeatherRepository weatherRepository;

    @Autowired
    CityRepository cityRepository;


    public WeatherDto getWeather(String city) {
    return weatherApiConnector.getWeather(cityTemp(city));
    }

    public List<WeatherStats> getStats(String city) {
        Optional<City> cityFound = cityRepository.findByName(cityTemp(city));
        log.info("leiti cityFound: {}", cityFound);
        List<Weather> weatherStatsByCity = weatherRepository.findByCityId(cityFound.get().getId());
        log.info("weatherStatsByCity: {}", weatherStatsByCity);
        List<WeatherStats> weatherStats = new ArrayList<>();
        for (Weather source : weatherStatsByCity) {
            WeatherStats target = new WeatherStats();
            target.setDateTime(source.getDateTime());
            target.setTemp(source.getTemp());
            target.setDescription(source.getDescription());
            target.setHumidity(source.getHumidity());
            target.setWindSpeed(source.getWindSpeed());
            weatherStats.add(target);
        }
        return weatherStats;
    }

    private Integer getCityId(String city){
        Optional<City> cityFound = cityRepository.findByName(cityTemp(city));
        if (cityFound.isPresent()) {}
        return cityFound.get().getId();
    }
    @Scheduled(fixedRate = 10000)
    public void saveWeatherTask() {
        Iterable<City> cities = cityRepository.findAll();
        log.info("cities {}", cities);
        for (City city : cities) {
            log.info("city.getName: {}", city.getName());
            saveWeather(city.getName());
        }
    }

    public void saveWeather(String city) {
        WeatherDto weatherDto = getWeather(city);
        Weather weather = new Weather();

        Temps temps = weatherDto.getTemps();
        Wind wind = weatherDto.getWind();
        weather.setTemp(temps.getTemp());
        weather.setHumidity(temps.getHumidity());
        weather.setWindSpeed(wind.getSpeed());
        weather.setDateTime(LocalDateTime.now());

        List<WeatherData> weatherData = weatherDto.getWeather();
        for (WeatherData data : weatherData){
            weather.setDescription(data.getDescription());
        }
        weather.setCityId(getCityId(city));
        weatherRepository.save(weather);
    }

    public String cityTemp(String city){
        String s = city.substring(0,1).toUpperCase() + city.substring(1).toLowerCase();
        return s;
    }

/*
    public LocalDateTime formattedDate (LocalDateTime dateTime) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
        String s = dateTime.toString();
        return LocalDateTime.parse(s, formatter);
    }
*/

}
