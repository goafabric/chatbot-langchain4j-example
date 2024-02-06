package org.goafabric.imperativebot.logic;

import org.goafabric.imperativebot.repository.entity.MedicalRecordType;
import org.goafabric.imperativebot.repository.MedicalRecordTypeRepository;
import org.goafabric.imperativebot.repository.entity.PatientName;
import org.goafabric.imperativebot.repository.PatientNamesRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class ImperativeChatBot {
    public record SearchResult(PatientName patientName, MedicalRecordType type) {}

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    private PatientNamesRepository patientNamesRepository = new PatientNamesRepository();
    private MedicalRecordTypeRepository medicalRecordTypeRepository = new MedicalRecordTypeRepository();


    public SearchResult find(String text) {
        var tokens = reduceText(text);
        var patient = tokens.stream()
                .map(this::findPatient)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);


        var medicalRecordType = tokens.stream()
                .map(this::findByType)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);

        return new SearchResult(patient, medicalRecordType);
    }

    public List<String> reduceText(String input) {
        var text = input.replaceAll(",", "");
        return Arrays.stream(text.split(" "))
                .filter(token -> token.length() > 3).toList();
    }

    public PatientName findPatient(String name) {
        return patientNamesRepository.findByName(name).orElse(null);
    }


    public MedicalRecordType findByType(String type) {
        return medicalRecordTypeRepository.findByType(type);
    }
}