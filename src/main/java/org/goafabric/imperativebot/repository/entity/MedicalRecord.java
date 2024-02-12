package org.goafabric.imperativebot.repository.entity;

public record MedicalRecord(
        String id,
        String patientId,
        MedicalRecordType type,
        String display,
        String code) {
}
