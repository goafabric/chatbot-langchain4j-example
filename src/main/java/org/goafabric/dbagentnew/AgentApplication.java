package org.goafabric.dbagentnew;

import org.goafabric.dbagentnew.ai.DatabaseAgent;
import org.goafabric.dbagentnew.persistence.DemoDataImporter;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportRuntimeHints;

import java.util.Scanner;


@SpringBootApplication
@ImportRuntimeHints(AgentApplication.ApplicationRuntimeHints.class)
public class AgentApplication {

    public static void main(String[] args){
        SpringApplication.run(AgentApplication.class, args);
    }

    /*
    I need to find bart
    Can you give me his address
    I need to find the person with allergies to work
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


    static class ApplicationRuntimeHints implements RuntimeHintsRegistrar {
        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            try {
                hints.proxies().registerJdkProxy(DatabaseAgent.class);
                hints.reflection().registerType(Class.forName("dev.ai4j.openai4j.chat.ChatCompletionResponse$Builder"),
                        MemberCategory.DECLARED_CLASSES, MemberCategory.INTROSPECT_DECLARED_METHODS, MemberCategory.INTROSPECT_DECLARED_CONSTRUCTORS);
                hints.reflection().registerType(Class.forName("dev.ai4j.openai4j.shared.Usage$Builder"),
                        MemberCategory.DECLARED_CLASSES, MemberCategory.INTROSPECT_DECLARED_METHODS, MemberCategory.INTROSPECT_DECLARED_CONSTRUCTORS);
                hints.reflection().registerType(Class.forName("dev.ai4j.openai4j.shared.CompletionTokensDetails$Builder"),
                        MemberCategory.DECLARED_CLASSES, MemberCategory.INTROSPECT_DECLARED_METHODS, MemberCategory.INTROSPECT_DECLARED_CONSTRUCTORS);
                hints.reflection().registerType(Class.forName("dev.ai4j.openai4j.shared.PromptTokensDetails$Builder"),
                        MemberCategory.DECLARED_CLASSES, MemberCategory.INTROSPECT_DECLARED_METHODS, MemberCategory.INTROSPECT_DECLARED_CONSTRUCTORS);
                hints.reflection().registerType(Class.forName("dev.ai4j.openai4j.chat.ChatCompletionChoice$Builder"),
                        MemberCategory.DECLARED_CLASSES, MemberCategory.INTROSPECT_DECLARED_METHODS, MemberCategory.INTROSPECT_DECLARED_CONSTRUCTORS);
                hints.reflection().registerType(Class.forName("dev.ai4j.openai4j.chat.Delta$Builder"),
                        MemberCategory.DECLARED_CLASSES, MemberCategory.INTROSPECT_DECLARED_METHODS, MemberCategory.INTROSPECT_DECLARED_CONSTRUCTORS);
                hints.reflection().registerType(Class.forName("dev.ai4j.openai4j.chat.Function$Builder"),
                        MemberCategory.DECLARED_CLASSES, MemberCategory.INTROSPECT_DECLARED_METHODS, MemberCategory.INTROSPECT_DECLARED_CONSTRUCTORS);
                hints.reflection().registerType(Class.forName("dev.ai4j.openai4j.chat.FunctionCall$Builder"),
                        MemberCategory.DECLARED_CLASSES, MemberCategory.INTROSPECT_DECLARED_METHODS, MemberCategory.INTROSPECT_DECLARED_CONSTRUCTORS);
                hints.reflection().registerType(Class.forName("dev.ai4j.openai4j.chat.Tool$Builder"),
                        MemberCategory.DECLARED_CLASSES, MemberCategory.INTROSPECT_DECLARED_METHODS, MemberCategory.INTROSPECT_DECLARED_CONSTRUCTORS);
                hints.reflection().registerType(Class.forName("dev.ai4j.openai4j.chat.ToolCall$Builder"),
                        MemberCategory.DECLARED_CLASSES, MemberCategory.INTROSPECT_DECLARED_METHODS, MemberCategory.INTROSPECT_DECLARED_CONSTRUCTORS);
            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }


}
