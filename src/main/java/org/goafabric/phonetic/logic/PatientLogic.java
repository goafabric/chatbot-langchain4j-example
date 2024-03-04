package org.goafabric.phonetic.logic;

import org.goafabric.phonetic.phonetic.MyColognePhonetic;
import org.goafabric.phonetic.repository.PatientEo;
import org.goafabric.phonetic.repository.PatientRepository;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Component
@Transactional
public class PatientLogic {
    private final MyColognePhonetic phonetic = new MyColognePhonetic();

    private final PatientRepository repository;

    public PatientLogic(PatientRepository repository) {
        this.repository = repository;
    }

    public Iterable<PatientEo> findAll() {
        return repository.findAll();
    }

    public List<PatientEo> findBy(String familyName) {
        return repository.findByFamilyNameContainsIgnoreCaseOrFamilySoundex(familyName.toLowerCase(), phonetic.encode(familyName));
    }

    public List<PatientEo> findBy(String familyName, String givenName) {
        return repository.findByFamilyNameAndGivenName(familyName.toLowerCase(), phonetic.encode(familyName), givenName, phonetic.encode(givenName));
    }

    public void save(PatientEo patient) {
        repository.save(new PatientEo(patient.getId(), patient.getGivenName(), phonetic.encode(patient.getGivenName()), patient.getFamilyName(), phonetic.encode(patient.getFamilyName())));
    }
}
