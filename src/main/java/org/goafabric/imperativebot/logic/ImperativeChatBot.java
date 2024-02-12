package org.goafabric.imperativebot.logic;

import org.goafabric.imperativebot.repository.entity.MedicalRecordType;
import org.goafabric.imperativebot.repository.entity.PatientName;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class ImperativeChatBot {
    public record SearchResult(PatientName patientName, List<MedicalRecordType> medicalRecordTypes, String displayText) {}

    private final ImperativeTool imperativeTool = new ImperativeTool();

    public SearchResult find(String text) {
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

        for (int i = 0; i < tokens.size(); i++) {
            String token =  tokens.get(i);
            if (keywords.contains(token) && (i +1 < tokens.size())) {
                if (!keywords.contains(tokens.get(i+1))) {
                    return tokens.get(i + 1);
                }
            }
        }

        return "";
    }




}
