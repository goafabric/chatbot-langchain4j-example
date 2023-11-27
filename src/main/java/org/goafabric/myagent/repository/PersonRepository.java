package org.goafabric.myagent.repository;

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

    public Person findPersonById(String personId) {
        return persons.stream()
                .filter(person -> person.personId.toLowerCase().equals(personId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Person not found"));
    }

    public Person findByFirstName(String firstName) {
        return persons.stream()
                .filter(person -> person.firstName().toLowerCase().equals(firstName.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Person with firstName '" + firstName + "' not found"));
    }

    public Person findByLastName(String lastName) {
        return persons.stream()
                .filter(person -> person.lastName().toLowerCase().equals(lastName.toLowerCase()))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Person with lastName '" + lastName + "' not found"));
    }


}
