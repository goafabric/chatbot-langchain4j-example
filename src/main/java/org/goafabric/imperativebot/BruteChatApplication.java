package org.goafabric.imperativebot;

import org.goafabric.imperativebot.logic.BruteChatBot;

import java.util.Scanner;


/**
 * Created by amautsch on 26.06.2015.
 */


public class BruteChatApplication {
    public static void main(String[] args){
        var  chatBot = new BruteChatBot();
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
    }


}
