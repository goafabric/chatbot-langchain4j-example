package org.goafabric.imperativebot.logic;

import org.goafabric.imperativebot.repository.MedicalRecordRepository;
import org.goafabric.imperativebot.repository.PatientNamesRepository;
import org.goafabric.imperativebot.repository.entity.MedicalRecord;
import org.goafabric.imperativebot.repository.entity.MedicalRecordType;
import org.goafabric.imperativebot.repository.entity.PatientName;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class BruteChatTool {
    private final PatientNamesRepository patientNamesRepository;
    private final MedicalRecordRepository medicalRecordRepository;

    public BruteChatTool(PatientNamesRepository patientNamesRepository, MedicalRecordRepository medicalRecordRepository) {
        this.patientNamesRepository = patientNamesRepository;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public PatientName findPatientViaDatabaseBruteForce(String name) {
        var patientNames = patientNamesRepository.findByName(name, "");
        if (!patientNames.isEmpty()) {
            return patientNames.get(0);
        } else {
            var patientNames2 = patientNamesRepository.findByName("", name);
            return !patientNames2.isEmpty() ? patientNames2.get(0) : null;
        }
    }

    public MedicalRecordType findMedicalRecordTypeViaKeyords(String type) {
        return switch (type.toLowerCase()) {
            case "anamnesis", "anamneses", "anamnese", "anamnesen" -> MedicalRecordType.ANAMNESIS;
            case "condition", "conditions", "diagnosis", "diagnose", "diagnosen" -> MedicalRecordType.CONDITION;
            case "charge", "charges", "chargeitem", "chargeitems", "leistung", "leistungen" -> MedicalRecordType.CHARGEITEM;
            case "finding", "findings", "befund", "befunde" -> MedicalRecordType.FINDING;
            case "therapy", "therapies", "therapie", "therapien" -> MedicalRecordType.THERAPY;
            case "bodymetrics" -> MedicalRecordType.BODY_METRICS;
            //case null -> throw new IllegalArgumentException("Type should not be null");
            default -> null;
        };
    }

    public List<MedicalRecord> findByPatientIdAndDisplayAndType(String patientId, String display, List<MedicalRecordType> types) {
        return medicalRecordRepository.findByPatientIdAndDisplayAndType(patientId, display, types);
    }

}
