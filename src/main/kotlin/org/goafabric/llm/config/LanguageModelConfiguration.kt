package org.goafabric.llm.config

import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.ollama.OllamaChatModel
import dev.langchain4j.model.openai.OpenAiChatModel
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.time.Duration

@Configuration
class LanguageModelConfiguration {
    @Bean
    @Profile("openai")
    fun chatModelOpenAi(): ChatModel? {
        return OpenAiChatModel.builder().apiKey("")
            .baseUrl("http://localhost:11434/v1")
            .modelName("gpt-oss:20b")
            .timeout(Duration.ofSeconds(60)).temperature(0.0)
            .build()
    }

    @Bean
    @Profile("ollama")
    fun chatModelOllama(): ChatModel? {
        return OllamaChatModel.builder()
            .baseUrl("http://localhost:11434")
            .modelName("llama3.1")
            .timeout(Duration.ofSeconds(30)).temperature(0.0)
            .build()
    }

    @Bean
    @Profile("deepseek")
    fun chatModelDeepSeek(): ChatModel? {
        return OpenAiChatModel.builder()
            .apiKey("x")
            .baseUrl("http://localhost:11434/v1")
            .modelName("deepseek-r1:8b")
            .timeout(Duration.ofSeconds(30)).temperature(0.0)
            .build()
    }
}
