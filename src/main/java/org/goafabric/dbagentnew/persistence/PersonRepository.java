package org.goafabric.dbagentnew.persistence;

import org.goafabric.dbagentnew.persistence.entity.PersonEo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<PersonEo, String> {

    List<PersonEo> findByFirstNameIgnoreCase(String firstName);

    List<PersonEo> findByLastNameIgnoreCase(String lastName);

    List<PersonEo> findByAddressStreetContainsIgnoreCase(String street);

    List<PersonEo> findByAddressCityContainsIgnoreCase(String city);

}

