package org.goafabric.soundex.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientRepository extends CrudRepository<PatientEo, String> {
    //@Query("SELECT pe1_0 FROM PatientEo pe1_0 " +
    //        "WHERE UPPER(pe1_0.familyName) like UPPER(CONCAT('%', :familyName, '%')) OR pe1_0.familySoundex = :familySoundex")
    List<PatientEo> findByFamilyNameContainsIgnoreCaseOrFamilySoundex(String familyName, String familySoundex);
}
