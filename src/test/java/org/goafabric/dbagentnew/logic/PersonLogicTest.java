package org.goafabric.dbagentnew.logic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PersonLogicTest {
    @Autowired
    PersonLogic personLogic;

    @Test
    void findAll() {
        assertThat(personLogic.findAll()).hasSize(3);
    }


    @Test
    void findByFirstName() {
        var person = personLogic.findByFirstName("homer");
        System.out.println(person);
        assertThat(person).hasSize(1);
        assertThat(person.getFirst().getAddress()).hasSize(1);
        assertThat(person.getFirst().getAllergy()).hasSize(1);
    }

    @Test
    void findByLastName() {
        var person = personLogic.findByLastName("sampson");

        System.out.println(person);
        assertThat(person).hasSize(2);
        assertThat(person.getFirst().getAddress()).hasSize(1);
        assertThat(person.getFirst().getAllergy()).hasSize(1);
    }

    @Test
    void findByCity() {
        var person = personLogic.findByCity("springfield");

        System.out.println(person);
        assertThat(person).hasSize(3);
        assertThat(person.getFirst().getAddress()).hasSize(1);
        assertThat(person.getFirst().getAllergy()).hasSize(1);

    }
}