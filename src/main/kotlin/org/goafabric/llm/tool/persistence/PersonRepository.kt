package org.goafabric.llm.tool.persistence

import org.goafabric.llm.tool.persistence.entity.Person
import org.springframework.data.repository.CrudRepository

interface PersonRepository : CrudRepository<Person, String> {
    fun findByFirstNameIgnoreCase(firstName: String): MutableList<Person>

    fun findByLastNameIgnoreCase(lastName: String): MutableList<Person>

    fun findByAddressStreetContainsIgnoreCase(street: String): MutableList<Person>

    fun findByAddressCityContainsIgnoreCase(city: String): MutableList<Person>

    fun findByAllergyAllergyContainsIgnoreCase(allergy: String): MutableList<Person>
}

