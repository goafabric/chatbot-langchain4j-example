package org.goafabric.imperativebot.logic;

import org.goafabric.imperativebot.repository.entity.MedicalRecordType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ImperativeChatBotTest {
    private ImperativeChatBot chatBot = new ImperativeChatBot();

    @Test
    public void findPatient() {
        assertThat(chatBot.findPatient("Homer").name()).isEqualTo("Homer Simpson");
        assertThat(chatBot.findPatient("Bart").name()).isEqualTo("Bart Simpson");
        assertThat(chatBot.findPatient("Simpson").name()).isEqualTo("Homer Simpson");
        assertThat(chatBot.findPatient("Monty").name()).isEqualTo("Monty Burns");
        assertThat(chatBot.findPatient("none")).isNull();
    }

    @Test
    public void reduceText() {
        assertThat(String.join(" ", chatBot.tokeniceText("I am searching for Simpson, Bart")))
                .isEqualTo("searching Simpson Bart");
    }


    @Test
    public void findByType() {
        assertThat(chatBot.findByMedicalRecordType("Anamnesis")).isEqualTo(MedicalRecordType.ANAMNESIS);
        assertThat(chatBot.findByMedicalRecordType("Diagnosis")).isEqualTo(MedicalRecordType.CONDITION);
        assertThat(chatBot.findByMedicalRecordType("Befund")).isEqualTo(MedicalRecordType.FINDING);
    }

    @Test
    public void findText() {
        assertThat(chatBot.find("I am searching all diagnosis for Homer").patientName().name()).isEqualTo("Homer Simpson");
        assertThat(chatBot.find("I am searching all diagnosis for Homer").type()).isEqualTo(MedicalRecordType.CONDITION); //todo multiple types
    }


}