package org.example.myagent.repository;

import dev.langchain4j.agent.tool.Tool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class PersonRepository {
    public record Person(String personId, String firstName, String lastName) {}

    private List<Person> persons;

    public PersonRepository() {
        this.persons = new ArrayList<>();
        persons.add(new Person("1", "Homer", "Simpson"));
        persons.add(new Person("2", "Bart", "Simpson"));
        persons.add(new Person("3", "Monty", "Burns"));
    }

    @Tool
    public Person findByFirstName(String firstName) {
        return persons.stream()
                .filter(person -> person.firstName().toLowerCase().equals(firstName.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Person with firstName '" + firstName + "' not found"));
    }

    @Tool
    public Person findByLastName(String lastName) {
        return persons.stream()
                .filter(person -> person.lastName().toLowerCase().equals(lastName.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Person with lastName '" + lastName + "' not found"));
    }

    @Autowired
    private AddressRepository addressRepository;

    @Tool
    public PersonRepository.Person findByCity(String city) {
        var address = addressRepository.findByCity(city);
        return persons.stream()
                .filter(person -> person.personId.toLowerCase().equals(address.personId()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Person not found"));
    }

    @Autowired
    private AllergyRepository allergyRepository;

    @Tool
    public PersonRepository.Person findByAllergy(String description) {
        var allergy = allergyRepository.findByAllergy(description);

        return persons.stream()
                .filter(person -> person.personId.toLowerCase().equals(allergy.personId()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Person not found"));
    }

}
