package org.goafabric.imperativebot.logic;

import org.goafabric.imperativebot.repository.entity.MedicalRecord;
import org.goafabric.imperativebot.repository.entity.MedicalRecordType;
import org.goafabric.imperativebot.repository.entity.PatientName;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class ImperativeChatBot {
    public record SearchResult(PatientName patientName, List<MedicalRecordType> medicalRecordTypes, String displayText) {}

    private final ImperativeTool imperativeTool = new ImperativeTool();

    public List<MedicalRecord> find(String text, String prevPatientId) {
        var searchResult = createSearchResult(text);

        searchResult.displayText();
        searchResult.medicalRecordTypes();

        var patientId = searchResult.patientName == null ? prevPatientId : searchResult.patientName.id();

        return imperativeTool.findByPatientIdAndDisplayAndType(patientId, searchResult.displayText(), searchResult.medicalRecordTypes());
    }

    public SearchResult createSearchResult(String text) {
        var tokens = tokeniceText(text);

        var medicalRecordTypes = searchMedicalRecordType(tokens);
        var patient = searchPatient(tokens);

        var displayText = searchDisplayText(tokens);

        return new SearchResult(patient, medicalRecordTypes, displayText);
    }

    public List<String> tokeniceText(String input) {
        var text = input.replaceAll(",", "").toLowerCase();
        return Arrays.stream(text.split(" "))
                .filter(token -> token.length() > 3).toList();
    }


    @Nullable
    private PatientName searchPatient(List<String> tokens) {
        return tokens.stream()
                .map(imperativeTool::findPatient)
                .filter(Objects::nonNull)
                .findFirst()
                .orElse(null);
    }

    @Nullable
    private List<MedicalRecordType> searchMedicalRecordType(List<String> tokens) {
        return tokens.stream()
                .map(imperativeTool::findByMedicalRecordType)
                .filter(Objects::nonNull)
                .toList();
    }

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
