package org.goafabric.imperativebot.logic;

import org.goafabric.imperativebot.repository.entity.MedicalRecordType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;

class BruteChatToolTest {
    private BruteChatTool tool = new BruteChatTool();

    @Test
    public void findPatient() {
        assertThat(tool.findPatient("Homer").toString()).isEqualTo("Homer Simpson");
        assertThat(tool.findPatient("Bart").toString()).isEqualTo("Bart Simpson");
        assertThat(tool.findPatient("Simpson").toString()).isEqualTo("Homer Simpson");
        assertThat(tool.findPatient("Monty").toString()).isEqualTo("Monty Burns");
        assertThat(tool.findPatient("none")).isNull();
    }

    @Test
    public void findByMedicalRecordType() {
        assertThat(tool.findByMedicalRecordType("Anamnesis")).isEqualTo(MedicalRecordType.ANAMNESIS);
        assertThat(tool.findByMedicalRecordType("Diagnosis")).isEqualTo(MedicalRecordType.CONDITION);
        assertThat(tool.findByMedicalRecordType("Befund")).isEqualTo(MedicalRecordType.FINDING);
    }

    @Test
    public void findByPatientIdAndDisplayAndType() {
        assertThat(tool.findByPatientIdAndDisplayAndType("1", "fatty", new ArrayList<>())).hasSize(1);
        assertThat(tool.findByPatientIdAndDisplayAndType("1", "sugar", Arrays.asList(MedicalRecordType.ANAMNESIS, MedicalRecordType.FINDING))).hasSize(1);

        assertThat(tool.findByPatientIdAndDisplayAndType("1", "", new ArrayList<>())).hasSize(11);

    }
}