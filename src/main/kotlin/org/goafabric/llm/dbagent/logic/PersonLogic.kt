package org.goafabric.llm.dbagent.logic

import dev.langchain4j.agent.tool.Tool
import org.goafabric.llm.dbagent.persistence.PersonRepository
import org.goafabric.llm.dbagent.persistence.entity.Person
import org.springframework.stereotype.Component
import java.util.stream.StreamSupport

@Component //@Transactional
class PersonLogic(private val personRepository: PersonRepository) {
    fun findAll(): MutableList<Person> {
        return StreamSupport.stream<Person>(personRepository.findAll().spliterator(), false)
            .toList()
    }


    @Tool
    fun findByFirstName(firstName: String): MutableList<Person> {
        return personRepository.findByFirstNameIgnoreCase(firstName)
    }

    @Tool
    fun findByLastName(lastName: String): MutableList<Person> {
        return personRepository.findByLastNameIgnoreCase(lastName)
    }

    @Tool
    fun findByCity(city: String): MutableList<Person> {
        return personRepository.findByAddressCityContainsIgnoreCase(city)
    }

    @Tool
    fun findByAllergy(allergy: String): MutableList<Person> {
        return personRepository.findByAllergyAllergyContainsIgnoreCase(allergy)
    }

    fun save(person: Person): Person {
        return personRepository.save<Person>(person)
    }
}
