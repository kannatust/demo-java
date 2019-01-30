package ee.valiit.demo.demo.service;

import ee.valiit.demo.demo.connector.WeatherApiConnector;
import ee.valiit.demo.demo.dto.weather.*;
import ee.valiit.demo.demo.model.city.City;
import ee.valiit.demo.demo.model.weather.Weather;
import ee.valiit.demo.demo.repository.CityRepository;
import ee.valiit.demo.demo.repository.WeatherRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.*;
import org.mockito.junit.MockitoJUnitRunner;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

@RunWith(MockitoJUnitRunner.class)
public class WeatherServiceTest {

    @Mock
    private WeatherApiConnector weatherApiConnector;
    @Mock
    private CityRepository cityRepository;
    @Mock
    private WeatherRepository weatherRepository;
    @Mock
    private CityService cityService;

    @InjectMocks
    private WeatherService weatherService;

    @Test
    public void getWeatherAllSuccess() {
        when(weatherApiConnector.getWeather("Tallinn")).thenReturn(getWeatherDto());

        WeatherDto weatherDto = weatherService.getWeather("Tallinn");
        assertEquals(12.2, weatherDto.getTemps().getTemp(), 0);
        assertEquals(15.5, weatherDto.getWind().getSpeed(), 0);
    }

    @Test
    public void getStatsAllSuccess() {
        when(cityRepository.findByName("Tallinn")).thenReturn(findCity());
        when(weatherRepository.findByCityId(4)).thenReturn(getWeatherStatsByCity());

        List<WeatherStats> weather = weatherService.getStats("Tallinn");
        WeatherStats target = new WeatherStats();
        for (WeatherStats source : weather) {
            target.setTemp(source.getTemp());
            target.setHumidity(source.getHumidity());
            target.setDescription(source.getDescription());
            target.setWindSpeed(source.getWindSpeed());
        }
        assertEquals(12.2, target.getTemp(), 0);
        assertEquals(99, target.getHumidity(), 0);
        assertEquals(15.5, target.getWindSpeed(), 0);
        assertEquals("hail", target.getDescription());
    }

    @Test
    public void saveWeatherAllSuccess() {
        //Weather weather = new Weather();
        weatherService.saveWeather(findCity().getName());

        verify(weatherRepository).save(getWeather());
    }

    private WeatherDto getWeatherDto() {
        WeatherDto weatherDto = new WeatherDto();
        Temps temps = new Temps();
        temps.setTemp(12.2);
        temps.setHumidity(99);

        Wind wind = new Wind();
        wind.setSpeed(15.5);

        WeatherData weatherData = new WeatherData();
        weatherData.setDescription("hail");
        weatherData.setMain("main");

        List<WeatherData> weatherList = new ArrayList<>();
        weatherList.add(weatherData);

        WeatherStats weatherStats = new WeatherStats();
        weatherStats.setHumidity(99);
        weatherStats.setWindSpeed(15.5);
        weatherStats.setDescription("hail");
        weatherStats.setTemp(12.2);
        weatherStats.setDateTime(LocalDateTime.now());

        List<WeatherStats> weatherStatsList = new ArrayList<>();
        weatherStatsList.add(weatherStats);

        weatherDto.setTemps(temps);
        weatherDto.setWind(wind);
        weatherDto.setWeather(weatherList);
        weatherDto.setWeatherStats(weatherStatsList);

        return weatherDto;
    }

    private City findCity() {
        City city = new City();
        city.setName("Tallinn");
        city.setId(4);
        return city;
    }

    private List<Weather> getWeatherStatsByCity() {
        List<Weather> weatherStatsByCity = new ArrayList<>();
        Weather weather = new Weather();
        weather.setDateTime(LocalDateTime.now());
        weather.setTemp(12.2);
        weather.setHumidity(99);
        weather.setWindSpeed(15.5);
        weather.setDescription("hail");
        //weather.setCityId();
        //weather.setId();
        weatherStatsByCity.add(weather);
        return weatherStatsByCity;
    }

    private Weather getWeather(){
        Weather weather = new Weather();
         weather.setTemp(12.2);
         weather.setWindSpeed(15.5);
         weather.setHumidity(99);
         weather.setDescription("hail");
        return weather;
    }

    @Test
    public void saveWeatherTask() {
    }



    @Test
    public void cityTemp() {
    }

    @Test
    public void stringToDate() {
    }

    @Test
    public void stringToCalendarStart() {
    }

    @Test
    public void stringToCalendarEnd() {
    }

    @Test
    public void getMaxOrMinTemps() {
    }

    @Test
    public void findByCityAndDate() {
    }

    @Test
    public void findMaxOrMinTemps() {
    }
}