package org.goafabric.chatbot;

import org.goafabric.chatbot.logic.ChatBotLogic;


/**
 * Created by amautsch on 26.06.2015.
 */

//@SpringBootApplication
public class ChatApplication {
    public static void main(String[] args){
        ChatBotLogic.run();
    }

    /*
    public static void main(String[] args){
        SpringApplication.run(Application.class, args);
    }

    @Bean
    public CommandLineRunner init(ApplicationContext context, ChatBotLogic chatBotLogic) {
        return args -> chatBotLogic.run();
    }

     */


}
