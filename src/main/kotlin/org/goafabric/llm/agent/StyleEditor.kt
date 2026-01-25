package org.goafabric.llm.agent

import dev.langchain4j.agentic.Agent
import dev.langchain4j.service.UserMessage
import dev.langchain4j.service.V



interface StyleEditor {
    @UserMessage(
        """
        You are a professional editor.
        Analyze and rewrite the following story to better fit and be more coherent with the {{style}} style.
        Return only the story and nothing else.
        The story is "{{story}}".
        
        """
    )
    @Agent("Edits a story to better fit a given style")
    fun editStory(@V("story") story: String, @V("style") style: String): String
}