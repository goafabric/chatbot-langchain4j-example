package org.example;

import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;

import static java.time.Duration.ofSeconds;

public class _00_HelloDevoxx {

    public static void main(String[] args) {

        ChatLanguageModel model = OpenAiChatModel.builder().apiKey(ApiKeys.OPENAI_API_KEY)
                .timeout(ofSeconds(20)).build();

        String response = model.generate("Say Hello Devoxx");

        System.out.println(response);
    }
}
