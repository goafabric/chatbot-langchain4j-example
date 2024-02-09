package org.goafabric.dbagent.ai;

import dev.langchain4j.service.SystemMessage;

public interface DatabaseAgent {
    @SystemMessage({
            "You are a database admin that can query the database for persons",
            "The persons can be queried by firstname or lastname or city or allergy", //it's important to list the keywords, without the model mixes things up
            "Allergy can be found in combination of the description and the language, which could be german or english"
    })
    String chat(String userMessage);
}