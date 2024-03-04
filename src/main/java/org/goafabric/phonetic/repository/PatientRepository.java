package org.goafabric.phonetic.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientRepository extends CrudRepository<PatientEo, String> {
    //@Query("SELECT pe1_0 FROM PatientEo pe1_0 " +
    //        "WHERE UPPER(pe1_0.familyName) like UPPER(CONCAT('%', :familyName, '%')) OR pe1_0.familySoundex = :familySoundex")
    List<PatientEo> findByFamilyNameContainsIgnoreCaseOrFamilySoundex(String familyName, String familySoundex);


    @Query("SELECT pe1_0 FROM PatientEo pe1_0 " +
            "WHERE (UPPER(pe1_0.familyName) LIKE UPPER(CONCAT('%', :familyName, '%')) OR pe1_0.familySoundex = :familySoundex) " +
            "AND (UPPER(pe1_0.givenName) LIKE UPPER(CONCAT('%', :givenName, '%')) OR pe1_0.givenSoundex = :givenSoundex)")
    List<PatientEo> findByFamilyNameAndGivenName(String familyName, String familySoundex, String givenName, String givenSoundex);
}
