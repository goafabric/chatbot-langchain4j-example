package org.goafabric.llm.dbagent

import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.service.AiServices
import org.goafabric.llm.dbagent.logic.PersonLogic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("agent")
class DatabaseAgentConfiguration {
    @Bean
    fun databaseAgent(chatLanguageModel: ChatModel?, personLogic: PersonLogic?): DatabaseAgent? {
        return AiServices.builder<DatabaseAgent?>(DatabaseAgent::class.java)
            .chatModel(chatLanguageModel)
            .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
            .tools(personLogic)
            .build()
    }
}
