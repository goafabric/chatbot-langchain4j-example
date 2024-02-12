package org.goafabric.imperativebot.logic;

import org.goafabric.imperativebot.repository.entity.MedicalRecordType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ImperativeChatBotTest {
    private ImperativeChatBot chatBot = new ImperativeChatBot();

    @Test
    public void reduceText() {
        assertThat(String.join(" ", chatBot.tokeniceText("I am searching for Simpson, Bart")))
                .isEqualTo("searching simpson bart");
    }
    @Test
    public void findNameAndType() {
        assertThat(chatBot.find("I am searching all Diagnosis for Homer").patientName().name()).isEqualTo("Homer Simpson");
        assertThat(chatBot.find("I am searching all Diagnosis and Anamnesis for Homer").medicalRecordTypes()).contains(MedicalRecordType.CONDITION);
        assertThat(chatBot.find("I am searching all Diagnosis and Anamnesis for Homer").medicalRecordTypes()).contains(MedicalRecordType.ANAMNESIS);

        assertThat(chatBot.find("").patientName()).isNull();
        assertThat(chatBot.find("").medicalRecordTypes()).isEmpty();
    }

    @Test
    public void findDisplayText() {
        assertThat(chatBot.find("I am searching for all Diagnosis with text sugar and mice").displayText()).isEqualTo("sugar");
        assertThat(chatBot.find("I am searching for all Diagnosis that contain sugar and mice").displayText()).isEqualTo("sugar");
        assertThat(chatBot.find("I am searching for all Diagnosis that contain text sugar and mice").displayText()).isEqualTo("sugar");
    }

}