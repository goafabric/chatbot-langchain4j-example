package org.goafabric.dbagentnew.logic;

import dev.langchain4j.agent.tool.Tool;
import org.goafabric.dbagentnew.persistence.PersonRepository;
import org.goafabric.dbagentnew.persistence.entity.PersonEo;
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

    public List<PersonEo> findAll() {
        return StreamSupport.stream(personRepository.findAll().spliterator(), false)
                .toList();
    }


    @Tool
    public List<PersonEo> findByFirstName(String firstName) {
        return personRepository.findByFirstNameIgnoreCase(firstName);
    }

    @Tool
    public List<PersonEo> findByLastName(String lastName) {
        return personRepository.findByLastNameIgnoreCase(lastName);
    }


    @Tool
    public List<PersonEo> findByCity(String city) {
        return personRepository.findByAddressCityContainsIgnoreCase(city);
    }

    public PersonEo save(PersonEo person) {
        return personRepository.save(person);
    }

}
