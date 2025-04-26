package org.goafabric.dbagentnew.dbagent;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import org.goafabric.dbagentnew.dbagent.logic.PersonLogic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class DatabaseAgentConfiguration {

    @Bean
    DatabaseAgent databaseAgent(ChatLanguageModel chatLanguageModel, PersonLogic personLogic) {
        return AiServices.builder(DatabaseAgent.class)
                .chatLanguageModel(chatLanguageModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .tools(personLogic)
                .build();
    }


}
