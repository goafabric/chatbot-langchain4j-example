package org.goafabric.imperativebot.repository;

import org.goafabric.imperativebot.repository.entity.MedicalRecord;
import org.goafabric.imperativebot.repository.entity.MedicalRecordType;

import java.util.ArrayList;
import java.util.List;

public class MedicalRecordRepository {
    private final List<MedicalRecord> medicalRecords;

    public MedicalRecordRepository() {
        medicalRecords = new ArrayList<>();

        var patientId = "1";
        medicalRecords.add(new MedicalRecord(null, patientId, MedicalRecordType.ANAMNESIS, "shows the tendency to eat a lot of sweets with sugar", ""));
        medicalRecords.add(new MedicalRecord(null, patientId,MedicalRecordType.FINDING,  "possible indication of Diabetes", ""));
        medicalRecords.add(new MedicalRecord(null, patientId,MedicalRecordType.CONDITION, "Diabetes mellitus Typ 1", "none"));
        medicalRecords.add(new MedicalRecord(null, patientId,MedicalRecordType.ANAMNESIS, "shows the behaviour to eat a lot of fatty fast food", ""));
        medicalRecords.add(new MedicalRecord(null, patientId,MedicalRecordType.FINDING,  "clear indication of Adipositas", ""));
        medicalRecords.add(new MedicalRecord(null, patientId,MedicalRecordType.CONDITION, "Adipositas", "E66.00"));
        medicalRecords.add(new MedicalRecord(null, patientId,MedicalRecordType.ANAMNESIS, "hears strange voices of Michael Meyers, who tells him to set a fire", ""));
        medicalRecords.add(new MedicalRecord(null, patientId,MedicalRecordType.FINDING,  "psychological disorder", ""));
        medicalRecords.add(new MedicalRecord(null, patientId,MedicalRecordType.CONDITION, "Pyromanie", "F63.1"));
        medicalRecords.add(new MedicalRecord(null, patientId,MedicalRecordType.CHARGEITEM, "normal examination", "GOÄ1"));
        medicalRecords.add(new MedicalRecord(null, patientId, MedicalRecordType.THERAPY, "We recommend a sugar and fat free diet", ""));
    }

    public List<MedicalRecord> findByPatientIdAndDisplayAndType(String patientId, String display, List<MedicalRecordType> types) {
        var records = medicalRecords.stream().filter(medicalRecord -> medicalRecord.patientId().equals(patientId)
                && medicalRecord.display().toLowerCase().contains(display.toLowerCase())).toList();
        return !types.isEmpty() ? records.stream().filter(medicalRecord -> types.contains(medicalRecord.type())).toList()
                                : records;
    }
}
