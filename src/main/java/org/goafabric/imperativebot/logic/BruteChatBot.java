package org.goafabric.imperativebot.logic;

import org.goafabric.imperativebot.repository.entity.MedicalRecord;
import org.goafabric.imperativebot.repository.entity.MedicalRecordType;
import org.goafabric.imperativebot.repository.entity.PatientName;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class BruteChatBot {
    public record SearchResult(PatientName patientName, List<MedicalRecordType> medicalRecordTypes, String displayText) {
        public boolean nothingFound() {
            return (patientName == null && medicalRecordTypes().isEmpty() && displayText.isEmpty());
        }
    }

    private final BruteChatTool bruteChatTool = new BruteChatTool();

    public List<MedicalRecord> chat(String text, String prevPatientId) {
        var searchResult = createSearchResult(text);
        var patientId = searchResult.patientName == null ? prevPatientId : searchResult.patientName.id();
        return searchResult.nothingFound()
                ? new ArrayList<>()
                : bruteChatTool.findByPatientIdAndDisplayAndType(patientId, searchResult.displayText(), searchResult.medicalRecordTypes());
    }

    public SearchResult createSearchResult(String text) {
        var tokens = tokenizeText(text);
        var medicalRecordTypes = searchMedicalRecordType(tokens);
        var displayText = searchDisplayText(tokens);
        var patient = searchPatient(tokens);

        return new SearchResult(patient, medicalRecordTypes, displayText);
    }

    public List<String> tokenizeText(String input) {
        var text = input.replaceAll(",", "").toLowerCase();
        return Arrays.stream(text.split(" "))
                .filter(token -> token.length() > 3).toList();
    }

    //search record types from a static swtiched list of matching keywords
    private List<MedicalRecordType> searchMedicalRecordType(List<String> tokens) {
        return tokens.stream()
                .map(bruteChatTool::findMedicalRecordTypeViaKeyords)
                .filter(Objects::nonNull)
                .toList();
    }

    //brute force search with the tokens, for patient names inside the db, returns first hit
    private PatientName searchPatient(List<String> tokens) {
        return tokens.stream()
                .map(bruteChatTool::findPatientViaDatabaseBruteForce)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    //search display text via Keywords
    private String searchDisplayText(List<String> tokens) {
        var keywords = Arrays.asList("text", "contain", "contains");
        for (int i = 0; i < tokens.size() - 1; i++) {
            var token = tokens.get(i);
            var nextToken = tokens.get(i + 1);

            if (keywords.contains(token) && !keywords.contains(nextToken)) {
                return nextToken;
            }
        }
        return "";
    }

}
