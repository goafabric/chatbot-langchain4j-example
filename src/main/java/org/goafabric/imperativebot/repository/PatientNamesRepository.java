package org.goafabric.imperativebot.repository;

import org.goafabric.imperativebot.repository.entity.PatientName;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class PatientNamesRepository {
    private List<PatientName> patientNames;

    public PatientNamesRepository() {
        this.patientNames = new ArrayList<>();
        patientNames.add(new PatientName("1", "Homer", "Simpson"));
        patientNames.add(new PatientName("2", "Bart", "Simpson"));
        patientNames.add(new PatientName("3", "Monty", "Burns"));
    }

    public Optional<PatientName> findByName(String givenName, String familyName) {
        if (StringUtils.hasText(givenName)) {
            return patientNames.stream()
                    .filter(person ->
                            person.givenName().toLowerCase().contains(givenName.toLowerCase())).findFirst();
        }

        if (StringUtils.hasText(familyName)) {
            return patientNames.stream()
                    .filter(person ->
                            person.familyName().toLowerCase().contains(familyName.toLowerCase())).findFirst();
        }
        return Optional.of(null);
    }
}
