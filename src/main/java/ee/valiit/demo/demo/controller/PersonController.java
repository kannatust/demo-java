package ee.valiit.demo.demo.controller;


import ee.valiit.demo.demo.model.Person;
import ee.valiit.demo.demo.service.PersonService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.websocket.server.PathParam;
import javax.xml.ws.WebServiceException;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Slf4j
@RestController
@RequestMapping("/person")
public class PersonController {


  @Autowired PersonService personService;

  @RequestMapping(value = "/{socialSecurityId:[\\d]+}", method = RequestMethod.GET)
  public Person getPerson(@PathVariable Long socialSecurityId){
    log.info("kas kood jouab siia {}", socialSecurityId);
    return personService.getPerson(socialSecurityId);

  }

  @RequestMapping(value = "/{firstName:[\\D]+}", method = RequestMethod.GET)
  public Collection<Person> getName(@PathVariable String firstName){
    log.info("kas kood jõuab nimeni {}", firstName);
    return personService.getName(firstName);
  }

  @RequestMapping(value = "", method = RequestMethod.POST)
    public void addPerson(@RequestBody Person person){
    personService.addPerson(person);
  }

  @RequestMapping(value = "/{socialSecurityId:[\\d]+}", method = RequestMethod.PUT)
    public void editName(@RequestBody Person value){
      personService.editName(value);

  }
}

