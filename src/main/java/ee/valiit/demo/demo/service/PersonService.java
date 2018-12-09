package ee.valiit.demo.demo.service;

import ee.valiit.demo.demo.model.Person;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
public class PersonService {

  public List<Person> personsList = new ArrayList<Person>();

  @PostConstruct
  public void init(){
    Person firstP = new Person();
    firstP.setFirstName("Miki");
    firstP.setLastName("Hiir");
    firstP.setSocialSecurityId(310123456L);

    Person secondP = new Person();
    secondP.setFirstName("Hagar");
    secondP.setLastName("Hirmus");
    secondP.setSocialSecurityId(340123456L);

    Person thridP = new Person();
    thridP.setFirstName("Lady");
    thridP.setLastName("Gaga");
    thridP.setSocialSecurityId(480123456L);

    personsList.add(firstP);
    personsList.add(secondP);
    personsList.add(thridP);
    log.info("init done");
  }

  public Person getPerson(Long socialSecurityId){
    log.info("personList size {} {}", personsList.size(), socialSecurityId);

    Optional<Person> person = personsList.stream().filter((p -> p.getSocialSecurityId() == socialSecurityId)).findAny();
    log.info("Person {}", person.toString());
    if (person.isPresent()){
      return  person.get();
    }
    return new Person();
  }
// miks just Collection? Listiga ei saanud hakkama..
  public Collection<Person> getName(String firstName) {
      Collection<Person> getName = personsList.stream()
              .filter((p -> p.getFirstName().equals(firstName)))
              .collect(Collectors.toList());
      if (getName.isEmpty()){
          log.info("nimega {} inimest ei leitud", firstName);
      }
      return getName;
  }

  public void addPerson(Person person) {
    personsList.add(person);
  }

  public void editName(Person modified) {
      // kindlasti saaks järgmise rea kirjutada lühemalt ja ilma getPersonist duplikeerimata, aga ei suutnud välja nuputada, kuidas.
      Optional <Person> person = personsList.stream().filter(p -> p.getSocialSecurityId() == modified.getSocialSecurityId()).findAny();
  if (person.isPresent()){
      //et numbri või tühja stringi korral jääks nimi muutmata
      if (modified.getFirstName().length() > 0 && modified.getFirstName().matches("\\D+")) {
          person.get().setFirstName(modified.getFirstName());
      }

      if (modified.getLastName().length() > 0 && modified.getLastName().matches("\\D+")) {
          person.get().setLastName(modified.getLastName());
      }
  }
  }
}