
package ee.valiit.demo.demo.repository;

import ee.valiit.demo.demo.model.weather.Weather;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.Calendar;
import java.util.List;

public interface WeatherRepository extends CrudRepository<Weather, String> {
    List<Weather> findByCityId(Integer cityId);
    List<Weather> findAll();
    @Query(nativeQuery = true, value="SELECT * FROM Weather WHERE date_time > ? AND date_time < ?")
    List<Weather> findByDate(Calendar dateInputStart, Calendar dateInputEnd);

}
