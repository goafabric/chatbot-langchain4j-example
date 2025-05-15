package org.goafabric.dbagentnew.dbagent;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import org.goafabric.dbagentnew.dbagent.logic.PersonLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

@Configuration
@Profile("agent")
public class DatabaseAgentConfiguration {

    @Bean
    DatabaseAgent databaseAgent(ChatModel chatLanguageModel, PersonLogic personLogic) {
        return AiServices.builder(DatabaseAgent.class)
                .chatModel(chatLanguageModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .tools(personLogic)
                .build();
    }


}
