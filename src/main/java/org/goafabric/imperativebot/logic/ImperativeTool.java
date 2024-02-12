package org.goafabric.imperativebot.logic;

import org.goafabric.imperativebot.repository.MedicalRecordRepository;
import org.goafabric.imperativebot.repository.MedicalRecordTypeRepository;
import org.goafabric.imperativebot.repository.PatientNamesRepository;
import org.goafabric.imperativebot.repository.entity.MedicalRecord;
import org.goafabric.imperativebot.repository.entity.MedicalRecordType;
import org.goafabric.imperativebot.repository.entity.PatientName;

import java.util.List;

public class ImperativeTool {
    private final PatientNamesRepository patientNamesRepository = new PatientNamesRepository();
    private final MedicalRecordTypeRepository medicalRecordTypeRepository = new MedicalRecordTypeRepository();
    private final MedicalRecordRepository medicalRecordRepository = new MedicalRecordRepository();

    public PatientName findPatient(String name) {
        var patientName = patientNamesRepository.findByName(name, "").orElse(null);
        return patientName != null
                ? patientName
                : patientNamesRepository.findByName("", name).orElse(null);
    }


    public MedicalRecordType findByMedicalRecordType(String type) {
        return medicalRecordTypeRepository.findByType(type);
    }

    public List<MedicalRecord> findByPatientIdAndDisplayAndType(String patientId, String display, List<MedicalRecordType> types) {
        return medicalRecordRepository.findByPatientIdAndDisplayAndType(patientId, display, types);
    }
}
