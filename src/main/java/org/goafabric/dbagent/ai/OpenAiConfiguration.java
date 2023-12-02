package org.goafabric.dbagent.ai;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static java.time.Duration.ofSeconds;

@Configuration
@Profile("openai")
public class OpenAiConfiguration {

    @Bean
    ChatLanguageModel chatModelOpenAi(DatabaseTool databaseTool) {
        return OpenAiChatModel.builder().apiKey("demo")
                .modelName("gpt-3.5-turbo")
                .timeout(ofSeconds(30)).temperature(0.0)
                .build();
    }

    @Bean
    DatabaseAgent databaseAgent(ChatLanguageModel chatLanguageModel, DatabaseTool databaseTool) {
        return AiServices.builder(DatabaseAgent.class)
                .chatLanguageModel(chatLanguageModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .tools(databaseTool)
                .build();
    }


}
