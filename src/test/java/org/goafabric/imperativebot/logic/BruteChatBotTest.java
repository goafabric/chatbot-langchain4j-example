package org.goafabric.imperativebot.logic;

import org.goafabric.imperativebot.repository.entity.MedicalRecordType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class BruteChatBotTest {
    private BruteChatBot chatBot = new BruteChatBot();

    @Test
    public void reduceText() {
        assertThat(String.join(" ", chatBot.tokenizeText("I am searching for Simpson, Bart")))
                .isEqualTo("searching simpson bart");
    }

    @Test
    public void createSearchResultNameAndType() {
        assertThat(chatBot.createSearchResult("I am searching all Diagnosis for Monty").patientName().getFullName()).isEqualTo("Monty Burns");
        assertThat(chatBot.createSearchResult("I am searching all Diagnosis and Anamnesis for Monty").medicalRecordTypes()).contains(MedicalRecordType.CONDITION);
        assertThat(chatBot.createSearchResult("I am searching all Diagnosis and Anamnesis for Monty").medicalRecordTypes()).contains(MedicalRecordType.ANAMNESIS);

        assertThat(chatBot.createSearchResult("").patientName()).isNull();
        assertThat(chatBot.createSearchResult("").medicalRecordTypes()).isEmpty();
    }

    @Test
    public void createSearchResultDisplayText() {
        assertThat(chatBot.createSearchResult("I am searching for all Diagnosis for Monty with text sugar and mice").displayText()).isEqualTo("sugar");
        assertThat(chatBot.createSearchResult("I am searching for all Diagnosis for Monty that contain sugar and mice").displayText()).isEqualTo("sugar");
        assertThat(chatBot.createSearchResult("I am searching for all Diagnosis for Monty that contain text sugar and mice").displayText()).isEqualTo("sugar");
    }

    @Test
    public void find() {
        var prevPatientId = "1";

        assertThat(chatBot.chat("hi", prevPatientId)).isEmpty();
        assertThat(chatBot.chat("I am searching for Monty", prevPatientId)).isNotEmpty();
        assertThat(chatBot.chat("I am searching for all Diagnosis for Homer", prevPatientId)).isEmpty(); //Homer has no data attached

        assertThat(chatBot.chat("I am searching all Diagnosis and Anamnesis for Bart that contain nothing", prevPatientId)).isEmpty();
        assertThat(chatBot.chat("I am searching all Diagnosis and Anamnesis", prevPatientId)).isNotEmpty();

        var medicalRecords1 = chatBot.chat("I am searching all Diagnosis and Anamnesis for Monty that contain sugar", prevPatientId);
        //medicalRecords1.stream().forEach(m -> System.out.println(m.toString()));
        assertThat(medicalRecords1).hasSize(1);

        assertThat(chatBot.chat("I am searching all Diagnosis and Anamnesis for Monty that contain", prevPatientId)).isNotEmpty();

        var medicalRecords2 = chatBot.chat("I am searching all Diagnosis and Anamnesis for Burns", prevPatientId);
        medicalRecords2.stream().forEach(m -> System.out.println(m.toString()));
        assertThat(medicalRecords2).hasSize(6);
    }

}