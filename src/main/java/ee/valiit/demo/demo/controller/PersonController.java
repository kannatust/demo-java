package ee.valiit.demo.demo.controller;

import ee.valiit.demo.demo.dto.person.PersonDto;
import ee.valiit.demo.demo.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;


@Slf4j
@RestController
public class PersonController {


  @Autowired PersonService personService;

  @RequestMapping(value = "", method = RequestMethod.GET)
  public List<PersonDto> getAll(){
    return personService.getAll();
  }

  @RequestMapping(value = "/person/{socialSecurityId:[\\d]+}", method = RequestMethod.GET)
  public PersonDto getPerson(@PathVariable String socialSecurityId){
    log.info("kas kood jouab siia {}", socialSecurityId);
    return personService.getPerson(socialSecurityId);

  }

  @RequestMapping(value = "/person/{firstName:[\\D]+}", method = RequestMethod.GET)
  public List<PersonDto> getByFirstName(@PathVariable String firstName){
    log.info("kas kood jõuab nimeni {}", firstName);
    return personService.getByFirstName(firstName);
  }


  @RequestMapping(value = "/", method = RequestMethod.POST)
    public void addPerson(@RequestBody PersonDto item){
    personService.addPerson(item);
  }

  @RequestMapping(value = "/person/{socialSecurityId:[\\d]+}", method = RequestMethod.PUT)
    public void editName(@PathVariable String socialSecurityId, @RequestBody PersonDto value){
    personService.editName(value);

  }

  @RequestMapping(value = "/person/{socialSecurityId:[\\d]+}", method = RequestMethod.DELETE)
  public void deletePerson(@PathVariable String socialSecurityId){
    personService.deletePerson(socialSecurityId);

  }
}

