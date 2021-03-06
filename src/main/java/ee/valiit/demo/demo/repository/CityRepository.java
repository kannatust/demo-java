package ee.valiit.demo.demo.repository;

import ee.valiit.demo.demo.model.city.City;
import org.springframework.data.repository.CrudRepository;

import java.util.List;
import java.util.Optional;

public interface CityRepository extends CrudRepository<City, String> {
    City findByName(String name);
     City findById(Integer id);
     List<City> findAll();
}
