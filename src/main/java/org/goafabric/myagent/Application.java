package org.goafabric.myagent;

import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.SystemMessage;
import org.goafabric.myagent.mock.MockChatModel;
import org.goafabric.myagent.repository.PersonTool;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.HashMap;
import java.util.Scanner;
import java.util.function.Function;


/**
 * Created by amautsch on 26.06.2015.
 */

@SpringBootApplication
public class Application {

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }

    /*
    I need to find bart
    Can you give me his address
    I need to find the person with allergies to peanuts
    Which allergies to we have in shelbyville
    */

    @Bean
    public ApplicationRunner applicationRunner (DatabaseAgent agent, PersonTool personTool) {

        return args -> {
            var  scanner = new Scanner(System.in);
            while (true) {
                System.out.print("[User]: ");
                var agentAnswer = agent.chat(scanner.nextLine());
                System.out.println("[Agent]; " + agentAnswer);
            }
        };
    }

    @Bean
    ChatLanguageModel chatModel(PersonTool personTool) {
        HashMap<String, Function<String, Object>> functions = new HashMap<>();
        functions.put("firstname", personTool::findByFirstName);
        functions.put("lastname", personTool::findByLastName);
        functions.put("city", personTool::findByCity);
        functions.put("allergy", personTool::findByAllergy);

        return new MockChatModel(functions);
        /*
        return OpenAiChatModel.builder().apiKey("demo")
                .modelName("gpt-3.5-turbo")
                .timeout(ofSeconds(30)).temperature(0.0)
                .build();

         */
    }

    @Bean
    DatabaseAgent databaseAgent(ChatLanguageModel chatLanguageModel,
                                PersonTool personTool) {
        return AiServices.builder(DatabaseAgent.class)
                .chatLanguageModel(chatLanguageModel)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(20))
                .tools(personTool)
                .build();
    }

    public interface DatabaseAgent {
        @SystemMessage({
                "You are a database admin that can query the database for persons",
                "The persons can be queried by firstname or lastname or city or allergy", //it's important to list the keywords, without the model mixes things up
        })
        String chat(String userMessage);
    }    
    
}
