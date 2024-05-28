package org.goafabric.imperativebot.logic;

import org.goafabric.imperativebot.repository.entity.MedicalRecord;
import org.goafabric.imperativebot.repository.entity.MedicalRecordType;
import org.goafabric.imperativebot.repository.entity.PatientEo;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;
import java.util.stream.IntStream;


@Component
public class BruteChatBot {
    private final MedicalRecordTool medicalRecordTool;

    public BruteChatBot(MedicalRecordTool medicalRecordTool) {
        this.medicalRecordTool = medicalRecordTool;
    }

    public record SearchResult(PatientEo patientName, List<MedicalRecordType> medicalRecordTypes, String displayText) {
        public boolean nothingFound() {
            return (patientName == null && medicalRecordTypes().isEmpty() && displayText.isEmpty());
        }
    }

    public List<MedicalRecord> chat(String text, String prevPatientId) {
        var searchResult = createSearchResult(text);
        var patientId = searchResult.patientName == null ? prevPatientId : searchResult.patientName.getId();
        return searchResult.nothingFound()
                ? new ArrayList<>()
                : medicalRecordTool.findByPatientIdAndDisplayAndType(patientId, searchResult.displayText(), searchResult.medicalRecordTypes());
    }

    public SearchResult createSearchResult(String text) {
        var tokens = tokenizeText(text);
        var medicalRecordTypes = searchMedicalRecordType(tokens);
        var displayText = searchDisplayText(tokens);
        var patient = searchPatient(tokens);

        return new SearchResult(patient, medicalRecordTypes, displayText);
    }

    //return list of token and also replace all "," and words shorter than 3 chars
    public List<String> tokenizeText(String input) {
        var text = input.replaceAll(",", "").toLowerCase();
        return Arrays.stream(text.split(" "))
                .filter(token -> token.length() > 3).toList();
    }

    //search record types with the tokens, from a static switched list of matching keywords
    private List<MedicalRecordType> searchMedicalRecordType(List<String> tokens) {
        return tokens.stream()
                .map(medicalRecordTool::findMedicalRecordTypeViaKeyords)
                .filter(Objects::nonNull)
                .toList();
    }

    //brute force search with the tokens, for patient names inside the db, returns first hit and only via GivenName OR FamilyName
    private PatientEo searchPatient(List<String> tokens) {
        return tokens.stream()
                .map(medicalRecordTool::findPatientViaDatabaseBruteForce)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    //search display text via Keywords
    private String searchDisplayText(List<String> tokens) {
        var keywords = Arrays.asList("text", "contain", "contains");
        return IntStream.range(0, tokens.size() - 1)
                .filter(i -> keywords.contains(tokens.get(i)) && !keywords.contains(tokens.get(i + 1)))
                .mapToObj(i -> tokens.get(i + 1))
                //.mapToObj(index -> String.join(" ", tokens.subList(index + 1, tokens.size())))
                .findFirst()
                .orElse("");
    }

}
