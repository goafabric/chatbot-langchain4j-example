package org.goafabric.llm.agent

import dev.langchain4j.agentic.AgenticServices
import dev.langchain4j.agentic.UntypedAgent
import dev.langchain4j.model.chat.ChatModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile

@Configuration
@Profile("agent")
class AgentFlowConfiguration {
    @Bean
    fun novelCreator(chatModel: ChatModel): NovelCreator {
        val creativeWriter: CreativeWriter? = AgenticServices
            .agentBuilder(CreativeWriter::class.java)
            .chatModel(chatModel)
            .outputKey("story")
            .build()

        val audienceEditor: AudienceEditor? = AgenticServices
            .agentBuilder(AudienceEditor::class.java)
            .chatModel(chatModel)
            .outputKey("story")
            .build()

        val styleEditor: StyleEditor? = AgenticServices
            .agentBuilder(StyleEditor::class.java)
            .chatModel(chatModel)
            .outputKey("story")
            .build()

        return AgenticServices
            .sequenceBuilder(NovelCreator::class.java)
            .subAgents(creativeWriter
                //, audienceEditor
                , styleEditor)
            .outputKey("story")
            .build()
    }

    @Bean
    fun story(novelCreator: NovelCreator): String {
        var text = novelCreator.createNovel("dragons and wizards",
            "young adults",
            "fantasy")  //comedy
        println(text)
        return text
    }
}
