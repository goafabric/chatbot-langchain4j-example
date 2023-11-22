package org.goafabric.chatbot.logic;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.example.ApiKeys;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static java.time.Duration.ofSeconds;

@Component
public class ChatBotLogic {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    public void run() {
        //prompt("Hello world!");
        prompt("What is the meaning of life ?");
    }

    private void prompt(String prompt) {
        ChatLanguageModel model = OpenAiChatModel.builder().apiKey(ApiKeys.OPENAI_API_KEY)
                .timeout(ofSeconds(20)).build();

        log.info(prompt);
        log.info("thinking ...");
        log.info(model.generate(prompt));
    }
}
