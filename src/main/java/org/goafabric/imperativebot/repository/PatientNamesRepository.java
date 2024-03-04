package org.goafabric.imperativebot.repository;

import org.goafabric.imperativebot.repository.entity.PatientName;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

@Component
public class PatientNamesRepository {
    private List<PatientName> patientNames;

    public PatientNamesRepository() {
        this.patientNames = new ArrayList<>();
        patientNames.add(new PatientName("1", "Monty", "Burns"));
        patientNames.add(new PatientName("2", "Bart", "Simpson"));
        patientNames.add(new PatientName("3", "Homer", "Simpson"));
    }

    public List<PatientName> findByName(String givenName, String familyName) {
        if (StringUtils.hasText(givenName)) {
            return patientNames.stream()
                    .filter(person -> person.givenName().toLowerCase().contains(givenName.toLowerCase())).toList();
        } else if (StringUtils.hasText(familyName)) {
            return patientNames.stream()
                    .filter(person -> person.familyName().toLowerCase().contains(familyName.toLowerCase())).toList();
        } else {
            return new ArrayList<>();
        }
    }
}
