package org.goafabric.dbagent.ai;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static java.time.Duration.ofSeconds;

@Configuration
public class AIConfiguration {

    @Bean
    @Profile("openai")
    ChatLanguageModel chatModelOpenAi(DatabaseTool databaseTool) {
        return OpenAiChatModel.builder().apiKey("demo")
                .modelName("gpt-4o-mini")
                .timeout(ofSeconds(30)).temperature(0.0)
                .build();
    }

    @Bean
    @Profile("ollama")
    ChatLanguageModel chatModelOllama(DatabaseTool databaseTool) {
        return OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("llama3.1")
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
