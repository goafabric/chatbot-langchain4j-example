package org.goafabric.soundex;

import org.goafabric.soundex.repository.PatientEo;
import org.goafabric.soundex.repository.PatientRepository;
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

    public void save(PatientEo patient) {
        repository.save(new PatientEo(patient.getId(), patient.getGivenName(), patient.getFamilyName(), phonetic.encode(patient.getFamilyName())));
    }
}
