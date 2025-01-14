package org.goafabric.dbagentnew;

import org.goafabric.dbagentnew.ai.DatabaseAgent;
import org.goafabric.dbagentnew.persistence.DemoDataImporter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;


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
    public CommandLineRunner init(ApplicationContext context, DatabaseAgent agent, DemoDataImporter demoDataImporter, @Value("${scanner.enabled:true}") Boolean scannerEnabled) {
        return args -> {
            if ((args.length > 0) && ("-check-integrity".equals(args[0]))) {
                SpringApplication.exit(context, () -> 0);
            }

            if (!scannerEnabled) {
                return;
            }

            demoDataImporter.run();
            var  scanner = new Scanner(System.in);
            while (true) {
                System.out.print("[User]: ");
                var agentAnswer = agent.chat(scanner.nextLine());
                System.out.println("[Agent]: " + agentAnswer);
            }

        };
    }



}
