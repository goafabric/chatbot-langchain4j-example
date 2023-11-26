package org.example.myagent.repository;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

@Component
public class PersonRepository {
    public record Person(String firstName, String lastName) {}

    private List<Person> persons;

    public PersonRepository() {
        this.persons = new ArrayList<>();
        persons.add(new Person("Homer", "Simpson"));
        persons.add(new Person("Bart", "Simpson"));
        persons.add(new Person("Monty", "Burns"));
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
