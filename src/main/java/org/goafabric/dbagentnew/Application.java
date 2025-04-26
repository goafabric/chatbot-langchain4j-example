package org.goafabric.dbagentnew;

import org.goafabric.dbagentnew.dbagent.DatabaseAgent;
import org.goafabric.dbagentnew.dbagent.persistence.DemoDataImporter;
import org.goafabric.dbagentnew.mcp.McpBot;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

/*
I need to find bart
Can you give me his address
I need to find the person with allergies to work
Which allergies to we have in shelbyville
*/


@SpringBootApplication
public class Application {
    public static void main(String[] args) {
        //System.setProperty("spring.datasource.url", "jdbc:postgresql://localhost:5432/postgres");
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner init(@Autowired(required = false) DatabaseAgent databaseAgent,
                                  @Autowired(required = false) McpBot mcpBot,
                                  @Value("${scanner.enabled:true}") Boolean scannerEnabled,
                                  DemoDataImporter demoDataImporter) {
        return args -> {
            if (!scannerEnabled) {
                return;
            }

            demoDataImporter.run();
            var  scanner = new Scanner(System.in);
            while (true) {
                System.out.print("[User]: ");
                if (databaseAgent != null) {
                    System.out.println("[Agent]: " + databaseAgent.chat(scanner.nextLine()));
                }
                if (mcpBot != null) {
                    System.out.println("[Agent]: " + mcpBot.chat(scanner.nextLine()));

                }

            }
        };
    }


}
