package org.goafabric.dbagent;

import org.goafabric.dbagent.ai.DatabaseAgent;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;


/**
 * Created by amautsch on 26.06.2015.
 */

@SpringBootApplication
public class AgentApplication {

    public static void main(String[] args){
        SpringApplication.run(AgentApplication.class, args);
    }

    /*
    I need to find bart
    Can you give me his address
    I need to find the person with allergies to peanuts
    Which allergies to we have in shelbyville
    */

    @Bean
    public ApplicationRunner applicationRunner (DatabaseAgent agent) {

        return args -> {
            var  scanner = new Scanner(System.in);
            while (true) {
                System.out.print("[User]: ");
                var agentAnswer = agent.chat(scanner.nextLine());
                System.out.println("[Agent]: " + agentAnswer);
            }
        };
    }


    
}
