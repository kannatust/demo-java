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
import java.util.*;
import java.time.LocalDateTime;

import static java.util.Collections.max;
import static java.util.Collections.min;


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

/* pole vaja nüüd, kus on olemas getMaxTemps ja getMinTemps
    public List<WeatherByDate> getTempsByDate(String dateInput){
        log.info("StringToCalendar(dateInput) {}", stringToCalendarStart(dateInput), stringToCalendarEnd(dateInput));
        List<Weather> weatherForDate = weatherRepository.findByDate(stringToCalendarStart(dateInput), stringToCalendarEnd(dateInput));
        log.info("kas kuupäeva kohta on baasis info? {}", weatherForDate);

        List<WeatherByDate> weatherStatsByDate = new ArrayList<>();
        for (Weather source : weatherForDate){
            WeatherByDate target = new WeatherByDate();
            City city = cityRepository.findById(source.getCityId());
            target.setName(city.getName());
            target.setTemp(source.getTemp());
            weatherStatsByDate.add(target);
            log.info("getTempsByDate meetodist tuli: {}", target);
        }
        return weatherStatsByDate;

    }
*/

    private Integer getCityId(String city){
        Optional<City> cityFound = cityRepository.findByName(cityTemp(city));
        if (cityFound.isPresent()) {}
        return cityFound.get().getId();
    }

    private String getCityName(Integer id) {
        City cityFound = cityRepository.findById(id);
        return cityFound.getName();
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

    public Date stringToDate(String date) {
        return (date == null ? null : java.sql.Date.valueOf(date));
    }

    public Calendar stringToCalendarStart(String dateStr) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(stringToDate(dateStr));
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.HOUR, 0);
        return cal;
    }

    public Calendar stringToCalendarEnd(String dateStr) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(stringToDate(dateStr));
        cal.set(Calendar.SECOND, 59);
        cal.set(Calendar.MINUTE, 59);
        cal.set(Calendar.HOUR, 59);
        return cal;
    }

    public List<WeatherByDate> getMaxTemps (String dateStr) {
        List<WeatherByDate> tempsByCityAndDate = new ArrayList<>();
        // leian kõik konkreetse kuupäeva tulemused Weather tüüpi listina
        List<Weather> weatherForDate = weatherRepository.findByDate(stringToCalendarStart(dateStr), stringToCalendarEnd(dateStr));

        // leian linna tulemused
        Map<Integer, List<Double>> map = findByCityAndDate(weatherForDate);

        // leian linna tulemustest max tempiga tulemuse
        tempsByCityAndDate = findMaxTemps(map);
        return tempsByCityAndDate;
    }

    public List<WeatherByDate> getMinTemps (String dateStr) {
        List<WeatherByDate> tempsByCityAndDate = new ArrayList<>();
        // leian kõik konkreetse kuupäeva tulemused Weather tüüpi listina
        List<Weather> weatherForDate = weatherRepository.findByDate(stringToCalendarStart(dateStr), stringToCalendarEnd(dateStr));

        // leian linna tulemused
        Map<Integer, List<Double>> map = findByCityAndDate(weatherForDate);

        // leian linna tulemustest min tempiga tulemuse
        tempsByCityAndDate = findMinTemps(map);
        return tempsByCityAndDate;
    }

    public Map<Integer, List<Double>> findByCityAndDate(List<Weather> list) {
        Map<Integer, List<Double>> resultsByCityAndDate = new HashMap<>();

        for (Weather source : list){
            WeatherByDate target = new WeatherByDate();
            target.setName(getCityName(source.getCityId()));
            target.setTemp(source.getTemp());
            List<Double> temps = new ArrayList<>();
            temps.add(target.getTemp());
            if (!resultsByCityAndDate.containsKey(source.getCityId())) {
                resultsByCityAndDate.put(source.getCityId(), temps);
            }
            else {
                resultsByCityAndDate.get(source.getCityId()).add(target.getTemp());
            }
        }
        log.info("resultsByCityAndDate sees on: {}", resultsByCityAndDate);
        return resultsByCityAndDate;
    }


    public List<WeatherByDate> findMaxTemps(Map<Integer, List<Double>> map) {
        List<WeatherByDate> maxTempList = new ArrayList<>();

        for (Integer key : map.keySet()) {
            List<Double> value = map.get(key);
            List<Object> list = new ArrayList<>();
            list.add(value);
            log.info("city = {}{}", getCityName(key), list);
            Double maxTempForCity = max(value);
            log.info("Max = {}", maxTempForCity);
            WeatherByDate maxTemps = new WeatherByDate();
            maxTemps.setTemp(maxTempForCity);
            maxTemps.setName(getCityName(key));
            maxTempList.add(maxTemps);
        }
        return maxTempList;
    }
    public List<WeatherByDate> findMinTemps(Map<Integer, List<Double>> map) {
        List<WeatherByDate> minTempList = new ArrayList<>();

        for (Integer key : map.keySet()) {
            List<Double> value = map.get(key);
            List<Object> list = new ArrayList<>();
            list.add(value);
            log.info("city = {}{}", getCityName(key), list);
            Double minTempForCity = min(value);
            log.info("Min = {}", minTempForCity);
            WeatherByDate minTemps = new WeatherByDate();
            minTemps.setTemp(minTempForCity);
            minTemps.setName(getCityName(key));
            minTempList.add(minTemps);
        }
        return minTempList;
    }
}


