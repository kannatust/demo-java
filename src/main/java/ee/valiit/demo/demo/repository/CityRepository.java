package ee.valiit.demo.demo.repository;

import ee.valiit.demo.demo.model.city.City;
import org.springframework.data.repository.CrudRepository;

import java.util.Optional;

public interface CityRepository extends CrudRepository<City, String> {
    Optional<City> findByName(String name);
     Optional<City> findById(Integer id);
}