package org.goafabric.dbagentnew.dbagent.persistence;

import org.goafabric.dbagentnew.dbagent.persistence.entity.Person;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<Person, String> {

    List<Person> findByFirstNameIgnoreCase(String firstName);

    List<Person> findByLastNameIgnoreCase(String lastName);

    List<Person> findByAddressStreetContainsIgnoreCase(String street);

    List<Person> findByAddressCityContainsIgnoreCase(String city);

    List<Person> findByAllergyAllergyContainsIgnoreCase(String allergy);

}

