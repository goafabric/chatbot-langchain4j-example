package org.goafabric.imperativebot.logic;

import org.goafabric.imperativebot.repository.entity.MedicalRecordType;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ImperativeChatBotTest {
    private ImperativeChatBot chatBot = new ImperativeChatBot();

    @Test
    public void reduceText() {
        assertThat(String.join(" ", chatBot.tokeniceText("I am searching for Simpson, Bart")))
                .isEqualTo("searching Simpson Bart");
    }
    @Test
    public void findText() {
        assertThat(chatBot.find("I am searching all diagnosis for Homer").patientName().name()).isEqualTo("Homer Simpson");
        assertThat(chatBot.find("I am searching all diagnosis and anamnesis for Homer").medicalRecordTypes()).contains(MedicalRecordType.CONDITION);
        assertThat(chatBot.find("I am searching all diagnosis and anamnesis for Homer").medicalRecordTypes()).contains(MedicalRecordType.ANAMNESIS);

        assertThat(chatBot.find("").patientName()).isNull();
        assertThat(chatBot.find("").medicalRecordTypes()).isEmpty();
    }

}