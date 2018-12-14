package ee.valiit.demo.demo.repository;

import ee.valiit.demo.demo.model.Person;
import org.springframework.data.repository.CrudRepository;

public interface PersonRepository extends CrudRepository<Person, String> {

    Person findBySocialSecurityId(String socialSecurityId);
    Person findByFirstName(String firstName);
    Iterable<Person> findByLastName(String lastName);


}