package org.goafabric.llm.logic

import org.assertj.core.api.Assertions
import org.goafabric.llm.tool.logic.PersonLogic
import org.goafabric.llm.tool.persistence.entity.Address
import org.goafabric.llm.tool.persistence.entity.Allergy
import org.goafabric.llm.tool.persistence.entity.Person
import org.goafabric.llm.tool.persistence.entity.Person.address
import org.goafabric.llm.tool.persistence.entity.Person.allergy
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
internal class PersonLogicTest {
    @JvmField
    @Autowired
    var personLogic: PersonLogic? = null

    @Test
    fun findAll() {
        Assertions.assertThat<Person>(personLogic!!.findAll()).hasSize(4)
    }


    @Test
    fun findByFirstName() {
        val person: MutableList<Person?> = personLogic!!.findByFirstName("homer")
        println(person)
        Assertions.assertThat<Person>(person).hasSize(1)
        Assertions.assertThat<Address>(person.getFirst().address).hasSize(1)
        Assertions.assertThat<Allergy>(person.getFirst().allergy).hasSize(1)
    }

    @Test
    fun findByLastName() {
        val person: MutableList<Person?> = personLogic!!.findByLastName("sampson")

        println(person)
        Assertions.assertThat<Person>(person).hasSize(2)
        Assertions.assertThat<Address>(person.getFirst().address).hasSize(1)
        Assertions.assertThat<Allergy>(person.getFirst().allergy).hasSize(1)
    }

    @Test
    fun findByCity() {
        val person: MutableList<Person?> = personLogic!!.findByCity("springfield")

        println(person)
        Assertions.assertThat<Person>(person).hasSize(3)
        Assertions.assertThat<Address>(person.getFirst().address).hasSize(1)
        Assertions.assertThat<Allergy>(person.getFirst().allergy).hasSize(1)
    }
}