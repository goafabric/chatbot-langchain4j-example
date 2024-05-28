package org.goafabric.imperativebot;

import org.goafabric.imperativebot.logic.BruteChatBot;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.context.annotation.Bean;

import java.util.Scanner;


/**
 * Created by amautsch on 26.06.2015.
 */


//@SpringBootApplication


public class BruteChatApplication {
    public static void main(String[] args){
        SpringApplication.run(BruteChatApplication.class, args);
    }

    //I am searching for monty
    //I need all conditions for monty

    @Bean
    public CommandLineRunner init(BruteChatBot chatBot) {
        return args -> {
            var  scanner = new Scanner(System.in);
            while (true) {
                System.out.print("[User]: ");
                var userMessage = scanner.nextLine();
                System.out.print("[Agent]: ");

                var medicalRecords = chatBot.chat(userMessage, "1");
                if (medicalRecords.isEmpty()) {
                    System.out.println("I found nothing");
                } else {
                    System.out.println("I've found the following data:");
                    medicalRecords.stream().forEach(m -> System.out.println(m.toString()));
                }
            }
        };
    }


}
