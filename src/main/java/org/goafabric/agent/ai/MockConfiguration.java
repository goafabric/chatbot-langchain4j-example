package org.goafabric.agent.ai;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import org.goafabric.agent.ai.mock.MockChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.HashMap;
import java.util.function.Function;

@Configuration
@Profile("mock")
public class MockConfiguration {

    @Bean
    ChatLanguageModel chatModelMock(DatabaseTool databaseTool) {
        HashMap<String, Function<String, Object>> functions = new HashMap<>();
        functions.put("firstname", databaseTool::findByFirstName);
        functions.put("lastname", databaseTool::findByLastName);
        functions.put("city", databaseTool::findByCity);
        functions.put("allergy", databaseTool::findByAllergy);
        //functions.put("hi", f -> "hi there");

        return new MockChatModel(functions);
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
