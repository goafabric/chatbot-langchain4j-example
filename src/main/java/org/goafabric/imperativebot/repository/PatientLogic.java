package org.goafabric.imperativebot.repository;

import org.goafabric.imperativebot.phonetic.MyColognePhonetic;
import org.goafabric.imperativebot.repository.entity.PatientEo;
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
        createDemoData();
    }
    
    public Iterable<PatientEo> findAll() {
        return repository.findAll();
    }

    public List<PatientEo> findByFamilyNameAndGivenName(String familyName, String givenName) {
        return repository.findByFamilyNameAndGivenName(familyName.toLowerCase(), phonetic.encode(familyName), givenName, phonetic.encode(givenName));
    }

    public void save(PatientEo patient) {
        repository.save(new PatientEo(patient.getId(), patient.getGivenName(), phonetic.encode(patient.getGivenName()), patient.getFamilyName(), phonetic.encode(patient.getFamilyName())));
    }

    private void createDemoData() {
        save(new PatientEo(null, "Bart", null, "Simpson", null));
        save(new PatientEo(null, "Homer", null, "Simpson", null));
        save(new PatientEo(null, "Monty", null,"Burns", null));
        save(new PatientEo(null, "Michael", null,"Meyers", null));
        save(new PatientEo(null, "Hans", null, "Müller", null));
    }
}
