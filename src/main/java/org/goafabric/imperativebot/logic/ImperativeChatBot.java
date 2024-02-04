package org.goafabric.imperativebot.logic;

import org.goafabric.imperativebot.repository.PatientName;
import org.goafabric.imperativebot.repository.PatientNamesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;


public class ImperativeChatBot {
    public record SearchResult(Optional<PatientName> patientName, String type) {}

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private PatientNamesRepository patientNamesRepository = new PatientNamesRepository();


    public SearchResult find(String text) {
        Optional<PatientName> patient = null;
        var tokens = reduceText(text);
        for (String token : tokens) {
            patient = findPatient(token);
            if (patient.isPresent()) {
                break;
            }
        }

        return new SearchResult(patient, null);
    }

    public List<String> reduceText(String input) {
        var text = input.replaceAll(",", "");
        return Arrays.stream(text.split(" "))
                .filter(token -> token.length() > 3).toList();
    }

    public Optional<PatientName> findPatient(String name) {
        return patientNamesRepository.findByName(name);
    }


}
