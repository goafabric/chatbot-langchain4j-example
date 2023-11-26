package org.example.myagent;

import org.example.myagent.ai.DatabaseAgent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;


/**
 * Created by amautsch on 26.06.2015.
 */

@SpringBootApplication
public class Application {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public ApplicationRunner applicationRunner (DatabaseAgent agent) {
        return args -> {
            Scanner scanner = new Scanner(System.in);

            while (true) {
                System.out.print("[User]: ");
                String userMessage = scanner.nextLine();
                String agentAnswer = agent.chat(userMessage);
                System.out.println("[Agent]; " + agentAnswer);
            }
        };
    }

    /*
    @Bean
    public CommandLineRunner init(ApplicationContext context, PersonRepository repository) {
        return args -> {
            System.out.println((repository.findByFirstName("Bart").toString()));
            System.out.println((repository.findByLastName("Simpson").toString()));
        };
    }
    */


}
