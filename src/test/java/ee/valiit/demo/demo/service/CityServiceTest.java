package ee.valiit.demo.demo.service;

import com.fasterxml.jackson.databind.ser.Serializers;
import ee.valiit.demo.demo.connector.CountryApiConnector;
import ee.valiit.demo.demo.dto.city.CityDto;
import ee.valiit.demo.demo.dto.country.BaseCountry;
import ee.valiit.demo.demo.dto.country.CountryDto;
import ee.valiit.demo.demo.dto.weather.Temps;
import ee.valiit.demo.demo.dto.weather.WeatherDto;
import ee.valiit.demo.demo.exception.ValidationException;
import ee.valiit.demo.demo.model.city.City;
import ee.valiit.demo.demo.repository.CityRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class CityServiceTest {
    @Mock
    private CityRepository cityRepository;
    @Mock
    private WeatherService weatherService;
    @Mock
    CountryApiConnector countryApiConnector;

    @InjectMocks
    private CityService cityService;

    @Test
    public void getCountryDataAllSuccess() {
        when(countryApiConnector.getCity("Estonia")).thenReturn(getCountryData());
        when(weatherService.getWeather("Tallinn")).thenReturn(getWeather());

        CountryDto countryDto = cityService.getCountryData("Estonia");
        assertEquals("Tallinn", countryDto.getCapital());
        assertEquals(12.2, countryDto.getTemp(), 0);
        assertEquals(100000+"", countryDto.getPopulation()+"");
    }

    @Test
    public void getCountryDataShouldReturnDataWithoutCurrentTempIfRegionNotEurope() {
        when(countryApiConnector.getCity("Canada")).thenReturn(getCountryDataRegionNotEurope());

        CountryDto countryDto = cityService.getCountryData("Canada");
        assertEquals("Ottawa", countryDto.getCapital());
        assertEquals(999999+"", countryDto.getPopulation()+"");
        assertNull(null, countryDto.getTemp());
    }

    @Test
    public void addCityAllSuccess() {
        City city = new City();
        city.setName("Tallinn");
        CityDto cityDto = new CityDto();
        cityDto.setName("Tallinn");

        when(cityRepository.findByName("Tallinn")).thenReturn(null);
        when(weatherService.cityTemporary("Tallinn")).thenReturn(city.getName());

        cityService.addCity(cityDto);

        verify(cityRepository).save(city);
    }

    @Test (expected = ValidationException.class)
    public void addCityShouldThrowValidationExceptionWhenCityAlreadyExists() {
        CityDto cityDto = new CityDto();
        cityDto.setName("Tallinn");

        when(cityRepository.findByName("Tallinn")).thenReturn(findCity());

        cityService.addCity(cityDto);
    }

    @Test
    public void deleteCityAllSuccess() {
        when(cityRepository.findByName("Tallinn")).thenReturn(findCity());
        when(weatherService.cityTemporary("Tallinn")).thenReturn(findCity().getName());

        cityService.deleteCity("Tallinn");

        verify(cityRepository).delete(findCity());
    }

    @Test (expected = ValidationException.class)
    public void deleteCityShoudlThrowValidationExceptionWhenCityNotFound() {

        cityService.deleteCity("kuku");
    }


    private List<BaseCountry> getCountryData(){
        List<BaseCountry> countryList = new ArrayList<>();
        BaseCountry estonia = new BaseCountry();
        estonia.setName("Estonia");
        estonia.setPopulation(100000);
        estonia.setCapital("Tallinn");
        estonia.setRegion("Europe");
        countryList.add(estonia);
        return countryList;
    }

    private List<BaseCountry> getCountryDataRegionNotEurope() {
        List<BaseCountry> countryList = new ArrayList<>();
        BaseCountry canada = new BaseCountry();
        canada.setName("Canada");
        canada.setPopulation(999999);
        canada.setCapital("Ottawa");
        canada.setRegion("Americas");
        countryList.add(canada);
        return countryList;
    }

    private WeatherDto getWeather(){
        WeatherDto weatherDto = new WeatherDto();
        Temps temps = new Temps();
        temps.setTemp(12.2);
        weatherDto.setTemps(temps);
        return weatherDto;
    }

    private City findCity() {
        City city = new City();
        city.setName("Tallinn");
        city.setId(4);
        return city;
    }

}