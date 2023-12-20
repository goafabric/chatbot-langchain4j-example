package org.goafabric.chatbot;

import org.goafabric.chatbot.ai.LocalAiChat;

import java.util.Scanner;


/**
 * Created by amautsch on 26.06.2015.
 */


public class ChatApplication {
    public static void main(String[] args){
        var  scanner = new Scanner(System.in);
        while (true) {
            System.out.print("[User]: ");
            var userMessage = scanner.nextLine();
            System.out.print("[Agent]: ");
            //OpenAiChat.chat(userMessage);
            //OllamaChat.chat(userMessage);
            LocalAiChat.chat(userMessage);
        }
    }


}
