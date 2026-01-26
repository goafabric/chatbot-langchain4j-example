package org.goafabric.llm.dbassistant

import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.service.AiServices
import org.goafabric.llm.dbassistant.logic.PersonLogic
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("allergy")
class DatabaseAsistantConfiguration {
    @Bean
    fun databaseAgent(chatLanguageModel: ChatModel?, personLogic: PersonLogic?): DatabaseAssistant? {
        return AiServices.builder<DatabaseAssistant?>(DatabaseAssistant::class.java)
            .chatModel(chatLanguageModel)
            .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
            .tools(personLogic)
            .build()
    }
}
