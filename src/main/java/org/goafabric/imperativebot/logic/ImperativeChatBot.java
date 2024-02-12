package org.goafabric.imperativebot.logic;

import org.goafabric.imperativebot.repository.entity.MedicalRecordType;
import org.goafabric.imperativebot.repository.entity.PatientName;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;


public class ImperativeChatBot {
    public record SearchResult(PatientName patientName, List<MedicalRecordType> medicalRecordTypes) {}

    private final ImperativeTool imperativeTool = new ImperativeTool();

    public SearchResult find(String text) {
        var tokens = tokeniceText(text);

        var medicalRecordTypes = searchMedicalRecordType(tokens);
        var patient = searchPatient(tokens);

        return new SearchResult(patient, medicalRecordTypes);
    }

    /*
    public List<String> reduce(List<String> tokens, List<String> search) {
        return tokens.stream().filter(token -> !search.contains(token)).toList();
    }
     */

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

    public List<String> tokeniceText(String input) {
        var text = input.replaceAll(",", "");
        return Arrays.stream(text.split(" "))
                .filter(token -> token.length() > 3).toList();
    }


}
