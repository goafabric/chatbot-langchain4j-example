package org.goafabric.imperativebot.logic;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class ImperativeChatBotTest {
    private ImperativeChatBot chatBot = new ImperativeChatBot();

    @Test
    public void findPatient() {
        assertThat(chatBot.findPatient("Homer").get().name()).isEqualTo("Homer Simpson");
        assertThat(chatBot.findPatient("Bart").get().name()).isEqualTo("Bart Simpson");
        assertThat(chatBot.findPatient("Simpson").get().name()).isEqualTo("Homer Simpson");
        assertThat(chatBot.findPatient("Monty").get().name()).isEqualTo("Monty Burns");
        assertThat(chatBot.findPatient("none")).isNotPresent();
    }

    @Test
    public void findText() {
        assertThat(chatBot.find("Homer").patientName().get().name()).isEqualTo("Homer Simpson");
    }

}