package org.example.myagent;

import org.example.myagent.repository.PersonRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;


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
    public CommandLineRunner init(ApplicationContext context, PersonRepository repository) {
        return args -> {
            System.out.println((repository.findByFirstName("Bart").toString()));
            System.out.println((repository.findByLastName("Simpson").toString()));
        };
    }


}
