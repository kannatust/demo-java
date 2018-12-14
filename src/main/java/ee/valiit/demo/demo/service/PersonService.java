package ee.valiit.demo.demo.service;

import ee.valiit.demo.demo.model.Person;
import ee.valiit.demo.demo.dto.PersonDto;
import ee.valiit.demo.demo.repository.PersonRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.*;

@Slf4j
@Service
public class PersonService {

    @Autowired
    PersonRepository personRepository;

    public List<PersonDto> getAll() {
        Iterable<Person> all = personRepository.findAll();
        //List<Person> getAllList = new ArrayList<>();
        //all.forEach(getAllList::add);
        List<PersonDto> getAllDto = new ArrayList<>();
        for (Person source : all) {
            PersonDto target = new PersonDto();
            BeanUtils.copyProperties(source, target);
            getAllDto.add(target);
        }
        log.info("vaatame, kas getAllDto on ok {}", getAllDto);
        return getAllDto;
    }

    public PersonDto getPerson(String socialSecurityId) {
        Person person = personRepository.findBySocialSecurityId(socialSecurityId);
        PersonDto personDto = new PersonDto();
        BeanUtils.copyProperties(person, personDto);

        return personDto;
    }

    public List<PersonDto> getByFirstName(String firstName) {
        List<PersonDto> foundByNameDto = new ArrayList<>();
    //    List<Person> allList = new ArrayList<>();
    //    List<Person> foundByName = new ArrayList<>();
        Iterable<Person> all = personRepository.findAll();
     //      all.forEach(allList::add);
        for (Person p : all) {
            if (firstName != null) {
                if (p.getFirstName().equals(firstName)) {
                    PersonDto dto = new PersonDto();
                    BeanUtils.copyProperties(p, dto);
                    foundByNameDto.add(dto);
                }
            }
        }

        return foundByNameDto;
    }

    public void addPerson(PersonDto personDto) {
        Person person = new Person();
        BeanUtils.copyProperties(personDto, person);
        person.setCreatedAt(LocalDateTime.now());
        person.setCreatedBy("anonymous");
        personRepository.save(person);
    }

    public void editName(PersonDto modified) {
        Person person = personRepository.findBySocialSecurityId(modified.getSocialSecurityId());
        BeanUtils.copyProperties(modified, person);

        //et numbri või tühja stringi korral jääks nimi muutmata
        if (modified.getFirstName().length() > 0 && modified.getFirstName().matches("\\D+")) {
            person.setFirstName(modified.getFirstName());
        }

        if (modified.getLastName().length() > 0 && modified.getLastName().matches("\\D+")) {
            person.setLastName((modified.getLastName()));
        }
        person.setModifiedAt(LocalDateTime.now());
        person.setModifiedBy("anonymous");
        personRepository.save(person);
    }

    //TODO - kas siia ka vaja DTO mängu tuua?
    public void deletePerson(String socialSecurityId) {
        Person person = personRepository.findBySocialSecurityId(socialSecurityId);
        personRepository.delete(person);
    }
}


