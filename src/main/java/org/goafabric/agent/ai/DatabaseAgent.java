package org.goafabric.agent.ai;

import dev.langchain4j.service.SystemMessage;

public interface DatabaseAgent {
    @SystemMessage({
            "You are a database admin that can query the database for persons",
            "The persons can be queried by firstname or lastname or city or allergy", //it's important to list the keywords, without the model mixes things up
    })
    String chat(String userMessage);
}