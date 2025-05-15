package org.goafabric.dbagentnew.ai;

import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.ollama.OllamaChatModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import static java.time.Duration.ofSeconds;

@Configuration
public class LanguageModelConfiguration {

    @Bean
    @Profile("openai")
    ChatModel chatModelOpenAi() {
        return OpenAiChatModel.builder().apiKey("demo")
                .modelName("gpt-4o-mini")
                .timeout(ofSeconds(30)).temperature(0.0)
                .build();
    }

    @Bean
    @Profile("ollama")
    ChatModel chatModelOllama() {
        return OllamaChatModel.builder()
                .baseUrl("http://localhost:11434")
                .modelName("llama3.1")
                .timeout(ofSeconds(30)).temperature(0.0)
                .build();
    }

    @Bean
    @Profile("deepseek")
    ChatModel chatModelDeepSeek() {
        return OpenAiChatModel.builder()
                .apiKey("x")
                .baseUrl("http://localhost:11434/v1")
                .modelName("deepseek-r1:8b")
                .timeout(ofSeconds(30)).temperature(0.0)
                .build();
    }


}
