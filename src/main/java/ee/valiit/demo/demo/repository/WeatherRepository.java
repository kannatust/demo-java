
package ee.valiit.demo.demo.repository;

import ee.valiit.demo.demo.model.weather.Weather;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.time.LocalDate;
import java.util.Date;
import java.util.List;

public interface WeatherRepository extends CrudRepository<Weather, String> {
    List<Weather> findByCityId(Integer cityId);
    List<Weather> findAll();
    @Query(nativeQuery = true, value="SELECT * FROM Weather WHERE dateInput = ? AND extract(hour FROM date_time) BETWEEN 0 AND 23 AND extract(minute FROM date_time) BETWEEN 0 AND 59")
    List<Weather> findByDate(String dateInput);
}
//@Query(nativeQuery = true, value="SELECT * FROM Weather WHERE humidity = ? AND extract(hour FROM date_time) BETWEEN 0 AND 23 AND extract(minute FROM date_time) BETWEEN 0 AND 59")

