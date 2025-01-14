package org.goafabric.dbagentnew.logic;

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

    public PersonEo getById(String id) {
        return personRepository.findById(id).orElseThrow();

    }

    public List<PersonEo> findByFirstName(String firstName) {
        return personRepository.findByFirstNameIgnoreCase(firstName);
    }

    public List<PersonEo> findByLastName(String lastName) {
        return personRepository.findByLastNameIgnoreCase(lastName);
    }

    public List<PersonEo> findByStreet(String street) {
        return personRepository.findByAddressStreetContainsIgnoreCase(street);
    }

    public List<PersonEo> findByCity(String city) {
        return personRepository.findByAddressCityContainsIgnoreCase(city);
    }

    public PersonEo save(PersonEo person) {
        return personRepository.save(person);
    }

}
