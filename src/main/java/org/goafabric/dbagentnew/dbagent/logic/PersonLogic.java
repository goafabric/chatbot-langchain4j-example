package org.goafabric.dbagentnew.dbagent.logic;

import dev.langchain4j.agent.tool.Tool;
import org.goafabric.dbagentnew.dbagent.persistence.PersonRepository;
import org.goafabric.dbagentnew.dbagent.persistence.entity.Person;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.StreamSupport;

@Component
//@Transactional
public class PersonLogic {

    private final PersonRepository personRepository;


    public PersonLogic(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public List<Person> findAll() {
        return StreamSupport.stream(personRepository.findAll().spliterator(), false)
                .toList();
    }


    @Tool
    public List<Person> findByFirstName(String firstName) {
        return personRepository.findByFirstNameIgnoreCase(firstName);
    }

    @Tool
    public List<Person> findByLastName(String lastName) {
        return personRepository.findByLastNameIgnoreCase(lastName);
    }

    @Tool
    public List<Person> findByCity(String city) {
        return personRepository.findByAddressCityContainsIgnoreCase(city);
    }

    @Tool
    public List<Person> findByAllergy(String allergy) {
        return personRepository.findByAllergyAllergyContainsIgnoreCase(allergy);
    }

    public Person save(Person person) {
        return personRepository.save(person);
    }

}
