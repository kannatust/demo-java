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


import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.stream.Collectors;

import static java.lang.Integer.parseInt;

@Slf4j
@Service
public class WeatherService {

    @Autowired
    WeatherApiConnector weatherApiConnector;

    @Autowired
    WeatherRepository weatherRepository;

    @Autowired
    CityRepository cityRepository;

    @PersistenceContext
    private EntityManager em;

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

    public List<WeatherByDate> getTempsByDate(String dateInput){

        List<Weather> weatherForDate = weatherRepository.findByDate(dateInput);
        log.info("kas kuupäeva kohta on baasis info? {}", weatherForDate);

        List<WeatherByDate> weatherStatsByDate = new ArrayList<>();
        for (Weather source : weatherForDate){
            WeatherByDate target = new WeatherByDate();
            City city = cityRepository.findById(source.getCityId());
            target.setName(city.getName());
            target.setTemp(source.getTemp());
            weatherStatsByDate.add(target);
            log.info("mis siin juhtub {}", target);
        }
        return weatherStatsByDate;

    }

    private Integer getCityId(String city){
        Optional<City> cityFound = cityRepository.findByName(cityTemp(city));
        if (cityFound.isPresent()) {}
        return cityFound.get().getId();
    }
    @Scheduled(cron ="0 0 * * * *")
    public void saveWeatherTask() {
        Iterable<City> cities = cityRepository.findAll();
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

    public LocalDate dateTimeToDate(LocalDateTime dateTime){
        return (dateTime == null ? null : dateTime.toLocalDate());
    }

    public Date StringToDate(String date) {
        //java.sql.Date localDate = java.sql.Date.valueOf(date);
        return (date == null ? null : java.sql.Date.valueOf(date));
        //return ( date == null ? null : LocalDate.parse(date));
    }

  /* public List<WeatherByDate> getMaxTemps (ArrayList list){

      // List<WeatherByDate> weatherForCityByDateList = new ArrayList<>();
        List<WeatherByDate> allByDate = new ArrayList(list);
        for (WeatherByDate source : allByDate) {
            List<WeatherByDate> weatherForCityByDateList = allByDate.stream().filter(x -> x.getName().equals(source.getName())).collect(Collectors.toList());
        }
        for (WeatherByDate source : weatherForCityByDateList) {
            List<WeatherByDate> maxByTemp = weatherForCityByDateList.max(Comparator.comparing(WeatherByDate::getTemp))
                    .orElseThrow(NoSuchElementException::new);
            List<WeatherByDate> maxByTemp = new ArrayList<>();
            maxByTemp.add()
        }

        }
    return list;
*/



}
