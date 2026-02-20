package org.goafabric.llm

import org.goafabric.llm.config.Assistant
import org.goafabric.llm.tool.persistence.DemoDataImporter
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.beans.factory.annotation.Value
import org.springframework.boot.CommandLineRunner
import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.context.annotation.Bean
import java.util.*

/*
I need to find bart
Can you give me his address
I need to find the person with allergies to work
Which allergies to we have in shelbyville
*/
@SpringBootApplication
class Application {
    @Bean
    fun init(
        @Autowired(required = false) assistant: Assistant?,
        @Value("\${scanner.enabled:true}") scannerEnabled: Boolean,
        demoDataImporter: DemoDataImporter
    ): CommandLineRunner {
        return CommandLineRunner { args: Array<String> ->
            if (!scannerEnabled) {
                return@CommandLineRunner
            }
            if (assistant == null) {
                return@CommandLineRunner
            }
            demoDataImporter.run()
            val scanner = Scanner(System.`in`)
            while (true) {
                print("[User]: ")
                println("[Assistant]: " + assistant.answer(scanner.nextLine()))
            }
        }
    }


    companion object {
        @JvmStatic
        fun main(args: Array<String>) {
            //System.setProperty("spring.datasource.url", "jdbc:postgresql://localhost:5432/postgres");
            SpringApplication.run(Application::class.java, *args)
        }
    }
}
