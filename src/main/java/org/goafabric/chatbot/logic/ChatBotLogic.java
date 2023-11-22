package org.goafabric.chatbot.logic;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ChatBotLogic {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    public void run() {
        helloWorld();
    }

    private  void helloWorld() {
        ChatLanguageModel model = OpenAiChatModel.withApiKey("demo");
        String answer = model.generate("Hello world!");
        log.info(answer);
    }
}
