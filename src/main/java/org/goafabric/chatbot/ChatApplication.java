package org.goafabric.chatbot;

import org.goafabric.chatbot.ai.OpenAiChat;

import java.util.Scanner;


/**
 * Created by amautsch on 26.06.2015.
 */


public class ChatApplication {
    public static void main(String[] args){
        var  scanner = new Scanner(System.in);
        while (true) {
            System.out.print("[User]: ");
            OpenAiChat.chat(scanner.nextLine());
            //OllamaChat.chat(scanner.nextLine());
        }
    }


}
