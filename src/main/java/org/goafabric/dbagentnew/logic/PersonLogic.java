package org.goafabric.dbagentnew.logic;

import dev.langchain4j.agent.tool.Tool;
import org.goafabric.dbagentnew.persistence.PersonRepository;
import org.goafabric.dbagentnew.persistence.entity.Person;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.StreamSupport;

@Component
@Transactional
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

    public Person save(Person person) {
        return personRepository.save(person);
    }

}
