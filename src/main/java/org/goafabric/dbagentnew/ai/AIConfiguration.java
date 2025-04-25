package org.goafabric.dbagentnew.ai;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import org.goafabric.dbagentnew.logic.PersonLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static java.time.Duration.ofSeconds;

@Configuration
public class AIConfiguration {

    @Bean
    @Profile("openai")
    ChatLanguageModel chatModelOpenAi() {
        return OpenAiChatModel.builder().apiKey("demo")
                .modelName("gpt-4o-mini")
                .timeout(ofSeconds(30)).temperature(0.0)
                .build();
    }

    @Bean
    @Profile("ollama")
    ChatLanguageModel chatModelOllama() {
        return OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("llama3.1")
                .timeout(ofSeconds(30)).temperature(0.0)
                .build();
    }

    @Bean
    @Profile("deepseek")
    ChatLanguageModel chatModelDeepSeek() {
        return OpenAiChatModel.builder()
                .apiKey("x")
                .baseUrl("http://localhost:11434/v1")
                .modelName("deepseek-r1:8b")
                .timeout(ofSeconds(30)).temperature(0.0)
                .build();
    }

    @Bean
    DatabaseAgent databaseAgent(ChatLanguageModel chatLanguageModel, PersonLogic personLogic) {
        return AiServices.builder(DatabaseAgent.class)
                .chatLanguageModel(chatLanguageModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .tools(personLogic)
                .build();
    }


}
