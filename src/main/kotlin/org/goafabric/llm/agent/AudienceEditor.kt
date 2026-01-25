package org.goafabric.llm.agent

import dev.langchain4j.agentic.Agent
import dev.langchain4j.service.UserMessage
import dev.langchain4j.service.V



interface AudienceEditor {
    @UserMessage(
        """
        You are a professional editor.
        Analyze and rewrite the following story to better align
        with the target audience of {{audience}}.
        Return only the story and nothing else.
        The story is "{{story}}".
        
        """
    )
    @Agent("Edits a story to better fit a given audience")
    fun editStory(@V("story") story: String?, @V("audience") audience: String?): String?
}