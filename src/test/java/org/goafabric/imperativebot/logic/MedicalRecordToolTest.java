package org.goafabric.imperativebot.logic;

import org.goafabric.imperativebot.repository.entity.MedicalRecordType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
class MedicalRecordToolTest {
    @Autowired
    private MedicalRecordTool tool;

    @Test
    public void findPatient() {
        assertThat(tool.findPatientViaDatabaseBruteForce("Homer").getFullName()).isEqualTo("Homer Simpson");
        assertThat(tool.findPatientViaDatabaseBruteForce("Bart").getFullName()).isEqualTo("Bart Simpson");
        assertThat(tool.findPatientViaDatabaseBruteForce("Simpson").getFullName()).isEqualTo("Bart Simpson");
        assertThat(tool.findPatientViaDatabaseBruteForce("Monty").getFullName()).isEqualTo("Monty Burns");
        assertThat(tool.findPatientViaDatabaseBruteForce("none")).isNull();

        assertThat(tool.findPatientViaDatabaseBruteForce("Bard").getFullName()).isEqualTo("Bart Simpson");
        assertThat(tool.findPatientViaDatabaseBruteForce("Burnz").getFullName()).isEqualTo("Monty Burns");
    }

    @Test
    public void findByMedicalRecordType() {
        assertThat(tool.findMedicalRecordTypeViaKeyords("Anamnesis")).isEqualTo(MedicalRecordType.ANAMNESIS);
        assertThat(tool.findMedicalRecordTypeViaKeyords("Diagnosis")).isEqualTo(MedicalRecordType.CONDITION);
        assertThat(tool.findMedicalRecordTypeViaKeyords("Befund")).isEqualTo(MedicalRecordType.FINDING);
    }

    @Test
    public void findByPatientIdAndDisplayAndType() {
        assertThat(tool.findByPatientIdAndDisplayAndType("1", "fatty", new ArrayList<>())).hasSize(1);
        assertThat(tool.findByPatientIdAndDisplayAndType("1", "sugar", Arrays.asList(MedicalRecordType.ANAMNESIS, MedicalRecordType.FINDING))).hasSize(1);

        assertThat(tool.findByPatientIdAndDisplayAndType("1", "", new ArrayList<>())).hasSize(11);

    }
}