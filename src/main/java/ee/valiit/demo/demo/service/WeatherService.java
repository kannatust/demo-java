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
        return weatherApiConnector.getWeather(cityTemporary(city));
    }

    public List<WeatherStats> getStats(String city) {
        City cityFound = cityRepository.findByName(cityTemporary(city));
        log.info("kas kood jõuab siia - cityfound = {}", cityFound);
        List<Weather> weatherStatsByCity = weatherRepository.findByCityId(cityFound.getId());
        log.info("kas kood jõuab siia - weatherStatsByCity= {}", weatherStatsByCity);

        List<WeatherStats> weatherStats = new ArrayList<>();
        for (Weather source : weatherStatsByCity) {
            WeatherStats target = new WeatherStats();
            target.setDateTime(source.getDateTime());
            target.setTemp(source.getTemp());
            target.setDescription(source.getDescription());
            target.setHumidity(source.getHumidity());
            target.setWindSpeed(source.getWindSpeed());
            weatherStats.add(target);
            log.info("kas kood jõuab siia - target = {}", target);
        }
        return weatherStats;
    }

/* pole vaja nüüd, kus on olemas getMaxTemps ja getMinTemps
    public List<WeatherByDate> getTempsByDate(String dateInput){
        List<Weather> weatherForDate = weatherRepository.findByDate(stringToCalendarStart(dateInput), stringToCalendarEnd(dateInput));
        List<WeatherByDate> weatherStatsByDate = new ArrayList<>();
        for (Weather source : weatherForDate){
            WeatherByDate target = new WeatherByDate();
            City city = cityRepository.findById(source.getCityId());
            target.setName(city.getName());
            target.setTemp(source.getTemp());
            weatherStatsByDate.add(target);
        }
        return weatherStatsByDate;
    }
*/
    private Integer getCityId(String city){
        City cityFound = cityRepository.findByName(cityTemporary(city));
        if (cityFound != null) {}
        return cityFound.getId();
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
        log.info("uus ilmainfo salvestati baasi: {}", weather);
    }

    public String cityTemporary(String city){
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
        cal.set(Calendar.HOUR, 23);
        return cal;
    }

    public List<WeatherByDate> getMaxOrMinTemps (String dateStr, Boolean isMax) {
        List<WeatherByDate> tempsByCityAndDate;
        // leian kõik konkreetse kuupäeva tulemused Weather tüüpi listina
        List<Weather> weatherForDate = weatherRepository.findByDate(stringToCalendarStart(dateStr), stringToCalendarEnd(dateStr));
        // leian linna tulemused
        Map<Integer, List<Double>> map = findByCityAndDate(weatherForDate);
        log.info("isMax = {}", isMax);
        if (isMax) {
            // leian linna tulemustest max tempiga tulemuse
            tempsByCityAndDate = findMaxOrMinTemps(map, isMax);
            return tempsByCityAndDate;
        }
        else {
            tempsByCityAndDate = findMaxOrMinTemps(map, isMax);
            return tempsByCityAndDate;
        }
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
        return resultsByCityAndDate;
    }

    public List<WeatherByDate> findMaxOrMinTemps(Map<Integer, List<Double>> map, Boolean isMax) {
        List<WeatherByDate> maxOrMinTempList = new ArrayList<>();
        for (Integer key : map.keySet()) {
            List<Double> value = map.get(key);
            List<Object> list = new ArrayList<>();
            list.add(value);
            log.info("city = {}{}", getCityName(key), list);

            if (isMax) {

                Double maxTempForCity = max(value);
                log.info("Max = {}", maxTempForCity);
                WeatherByDate maxTemps = new WeatherByDate();
                maxTemps.setTemp(maxTempForCity);
                maxTemps.setName(getCityName(key));
                maxOrMinTempList.add(maxTemps);
            } else {
                Double minTempForCity = min(value);

                log.info("Min = {}", minTempForCity);
                WeatherByDate minTemps = new WeatherByDate();
                minTemps.setTemp(minTempForCity);
                minTemps.setName(getCityName(key));
                maxOrMinTempList.add(minTemps);
            }
        }
        return maxOrMinTempList;
    }
}


