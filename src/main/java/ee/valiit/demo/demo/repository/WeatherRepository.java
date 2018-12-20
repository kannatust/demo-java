
package ee.valiit.demo.demo.repository;

import ee.valiit.demo.demo.model.weather.Weather;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface WeatherRepository extends CrudRepository<Weather, String> {
    List<Weather> findByCityId(Integer cityId);
    List<Weather> findAll();
}

