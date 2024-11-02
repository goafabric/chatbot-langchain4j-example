package org.goafabric.imperativebot.logic;

import org.goafabric.imperativebot.repository.MedicalRecordRepository;
import org.goafabric.imperativebot.repository.entity.MedicalRecord;
import org.goafabric.imperativebot.repository.entity.MedicalRecordType;
import org.goafabric.imperativebot.repository.PatientLogic;
import org.goafabric.imperativebot.repository.entity.PatientEo;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MedicalRecordTool {
    private final PatientLogic patientLogic;
    private final MedicalRecordRepository medicalRecordRepository;

    public MedicalRecordTool(PatientLogic patientLogic, MedicalRecordRepository medicalRecordRepository) {
        this.patientLogic = patientLogic;
        this.medicalRecordRepository = medicalRecordRepository;
    }

    public PatientEo findPatientViaDatabaseBruteForce(String name) {
        var patientNames = patientLogic.findByFamilyNameAndGivenName(name, "");
        if (!patientNames.isEmpty()) {
            return patientNames.getFirst();
        } else {
            var patientNames2 = patientLogic.findByFamilyNameAndGivenName("", name);
            return !patientNames2.isEmpty() ? patientNames2.getFirst() : null;
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
