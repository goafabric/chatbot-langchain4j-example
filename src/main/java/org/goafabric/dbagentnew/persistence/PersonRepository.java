package org.goafabric.dbagentnew.persistence;

import org.goafabric.dbagentnew.persistence.entity.PersonEo;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PersonRepository extends CrudRepository<PersonEo, String> {

    List<PersonEo> findAll();

    List<PersonEo> findByFirstName(String firstName);

    List<PersonEo> findByLastName(String lastName);

    List<PersonEo> findByAddressStreetContainsIgnoreCase(String street);

    List<PersonEo> findByAddressCityContainsIgnoreCase(String city);

}

