package org.goafabric.llm.config

interface Assistant {
    fun answer(userMessage: String?): String?
}
