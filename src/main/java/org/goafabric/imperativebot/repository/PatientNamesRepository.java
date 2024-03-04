package org.goafabric.imperativebot.repository;

import org.apache.commons.codec.language.ColognePhonetic;
import org.goafabric.imperativebot.repository.entity.PatientName;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class PatientNamesRepository {
    private List<PatientName> patientNames;
    private final ColognePhonetic phonetic = new ColognePhonetic();

    public PatientNamesRepository() {
        this.patientNames = new ArrayList<>();
        patientNames.add(new PatientName("1", "Monty", phonetic.encode("Monty"), "Burns", phonetic.encode("Burns")));
        patientNames.add(new PatientName("2", "Bart", phonetic.encode("Bart"), "Simpson", phonetic.encode("Simpson")));
        patientNames.add(new PatientName("3", "Homer", phonetic.encode("Homer"), "Simpson", phonetic.encode("Simpson")));
    }

    public List<PatientName> findByFamilyNameAndGivenName(String familyName, String givenName) {
        if (StringUtils.hasText(familyName)) {
            return patientNames.stream()
                    .filter(person -> person.familyName().toLowerCase().contains(familyName.toLowerCase())).toList();
        }else if (StringUtils.hasText(givenName)) {
            return patientNames.stream()
                    .filter(person -> person.givenName().toLowerCase().contains(givenName.toLowerCase())).toList();
        } else {
            return new ArrayList<>();
        }
    }

}
