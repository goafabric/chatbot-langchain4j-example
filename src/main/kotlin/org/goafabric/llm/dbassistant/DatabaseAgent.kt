package org.goafabric.llm.dbassistant

import dev.langchain4j.service.SystemMessage
import org.goafabric.llm.config.Assistant

interface DatabaseAgent: Assistant {
    @SystemMessage(
        "You are a database admin that can query the database for persons",
        "The persons can be queried by firstname or lastname or city or allergy" //it's important to list the keywords, without the model mixes things up
    )
    fun chat(userMessage: String?): String?
}