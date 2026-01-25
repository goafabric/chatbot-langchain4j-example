package org.goafabric.llm.agent

import dev.langchain4j.agentic.Agent
import dev.langchain4j.service.V

interface NovelCreator {
    @Agent
    fun createNovel(@V("topic") topic: String, @V("audience") audience: String, @V("style") style: String): String
}