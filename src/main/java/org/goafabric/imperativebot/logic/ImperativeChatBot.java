package org.goafabric.imperativebot.logic;

import org.goafabric.imperativebot.repository.PatientName;
import org.goafabric.imperativebot.repository.PatientNamesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Optional;


public class ImperativeChatBot {
    public record SearchResult(Optional<PatientName> patientName, String type) {}

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    PatientNamesRepository patientNamesRepository = new PatientNamesRepository();


    public Optional<PatientName> findPatient(String name) {
        return patientNamesRepository.findByName(name);
    }

    public SearchResult find(String text) {
        var patient = findPatient("homer");
        return new SearchResult(patient, null);
    }

    public void find() {
        var searchResult = find("homer");
        if (searchResult.patientName().isPresent()) {
            log.info("found patient: " + searchResult.patientName().get());
        }
    }


}
