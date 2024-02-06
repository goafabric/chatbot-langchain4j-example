package org.goafabric.soundex.repository;

import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface PatientRepository extends CrudRepository<PatientEo, String> {
    List<PatientEo> findByFamilyName(String familyName);
}
