package org.example.myagent.ai;

import dev.langchain4j.service.SystemMessage;

public interface DatabaseAgent {

    @SystemMessage({
            "You are a database admin that can query the database for persons",
            "The persons can be queried by firstname or lastname"
    })
    String chat(String userMessage);
}