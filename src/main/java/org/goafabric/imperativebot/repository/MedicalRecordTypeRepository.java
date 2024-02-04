package org.goafabric.imperativebot.repository;

import org.goafabric.imperativebot.repository.entity.MedicalRecordType;

public class MedicalRecordTypeRepository {

    public MedicalRecordType findByType(String type) {

        return switch (type.toLowerCase()) {
            case "anamnesis", "anamneses", "anamnese", "anamnesen" -> MedicalRecordType.ANAMNESIS;
            case "condition", "conditions", "diagnosis", "diagnose", "diagnosen" -> MedicalRecordType.CONDITION;
            case "charge", "charges", "chargeitem", "chargeitems", "leistung", "leistungen" -> MedicalRecordType.CHARGEITEM;
            case "finding", "findings", "befund", "befunden" -> MedicalRecordType.FINDING;
            case "therapy", "therapies", "therapie", "therapien" -> MedicalRecordType.THERAPY;
            case "bodymetrics" -> MedicalRecordType.BODY_METRICS;
            //case null -> throw new IllegalArgumentException("Type should not be null");
            default -> null;
        };

    }
}
