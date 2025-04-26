package org.goafabric.dbagentnew;

import org.goafabric.dbagentnew.dbagent.persistence.DemoDataImporter;
import org.goafabric.dbagentnew.mcp.McpBot;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;

//@SpringBootApplication
public class McpApplication {
    public static void main(String[] args) {
        System.setProperty("spring.datasource.url", "jdbc:postgresql://localhost:5432/postgres");
        SpringApplication.run(McpApplication.class, args);
    }

    @Bean
    public CommandLineRunner init(McpBot mcpBot, DemoDataImporter demoDataImporter) {
        return args -> {
            demoDataImporter.run();
            var  scanner = new Scanner(System.in);
            while (true) {
                System.out.print("[User]: ");
                var agentAnswer = mcpBot.chat(scanner.nextLine());
                System.out.println("[Agent]: " + agentAnswer);
            }
        };
    }


}
