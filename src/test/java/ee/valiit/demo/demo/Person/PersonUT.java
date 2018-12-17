/*
package ee.valiit.demo.demo.Person;

import ee.valiit.demo.demo.dto.person.PersonDto;
import ee.valiit.demo.demo.model.Person;
import org.junit.Test;
import org.modelmapper.ModelMapper;

import static org.junit.Assert.assertEquals;


public class PersonUT {
    private static final ModelMapper modelMapper = new ModelMapper();

    @Test
    public void checkPersonMapping() {
        PersonDto personDto = new PersonDto();
        personDto.setFirstName("Testing firstName");
        personDto.setLastName("Testing lastName");
        personDto.setSocialSecurityId("Testing socialSecurityId");

        Person person = modelMapper.map(personDto, Person.class);
        assertEquals(personDto.getFirstName(), person.getFirstName());
        assertEquals(personDto.getLastName(), person.getLastName());
        assertEquals(personDto.getSocialSecurityId(), person.getSocialSecurityId());
        assertEquals(personDto.getCreatedAt(), person.getCreatedAt());
        assertEquals(personDto.getModifiedAt(), person.getModifiedAt());
    }
}*/
