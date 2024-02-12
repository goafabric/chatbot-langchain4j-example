package org.goafabric.imperativebot.logic;

import org.goafabric.imperativebot.repository.entity.MedicalRecord;
import org.goafabric.imperativebot.repository.entity.MedicalRecordType;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class ImperativeToolTest {
    private ImperativeTool tool = new ImperativeTool();

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
        List<MedicalRecord> recs1 = tool.findByPatientIdAndDisplayAndType("1", "fatty", new ArrayList<>());
        assertThat(recs1).hasSize(1);
        recs1.forEach(r -> System.out.println(r));

        List<MedicalRecord> recs2 = tool.findByPatientIdAndDisplayAndType("1", "sugar", Arrays.asList(MedicalRecordType.ANAMNESIS, MedicalRecordType.FINDING));
        recs2.forEach(r -> System.out.println(r));
        assertThat(recs2).hasSize(1);

        assertThat(tool.findByPatientIdAndDisplayAndType("1", "", new ArrayList<>())).hasSize(11);

    }
}