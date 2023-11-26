package org.example.myagent.ai;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import org.example.myagent.repository.AddressRepository;
import org.example.myagent.repository.PersonRepository;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import static java.time.Duration.ofSeconds;

@Configuration
public class LanguageModelConfiguration {
    @Bean
    ChatLanguageModel chatModel() {
        return OpenAiChatModel.builder().apiKey("demo")
                .modelName("gpt-3.5-turbo")
                .timeout(ofSeconds(30)).temperature(0.0)
                .build();
    }

    @Bean
    DatabaseAgent databaseAgent(ChatLanguageModel chatLanguageModel,
                                PersonRepository personRepository, AddressRepository addressRepository) {
        return AiServices.builder(DatabaseAgent.class)
                .chatLanguageModel(chatLanguageModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .tools(personRepository, addressRepository)
                //.retriever(retriever)
                .build();
    }

    public interface DatabaseAgent {

        @SystemMessage({
                "You are a database admin that can query the database for persons",
                "The persons can be queried by firstname or lastname"
        })
        String chat(String userMessage);
    }
}
