package org.goafabric.chatbot.logic;

import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.ollama.OllamaStreamingLanguageModel;
import dev.langchain4j.model.openai.OpenAiChatModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import static java.time.Duration.ofSeconds;

@Component
public class ChatBotLogic {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    public void run() {
        var prompt = "What do you know about Spring Boot, in a short answer ?";
        promptOpenAi(prompt);
        //promptLama(prompt);
    }

    private void promptOpenAi(String prompt) {
        var model = OpenAiChatModel.builder().apiKey("demo").timeout(ofSeconds(30)).build();
        System.out.println("Calling remote chatgpt \uD83E\uDD16 with question:\n" + prompt + "\n");
        System.out.println(model.generate(prompt));

        /*
        var model = OpenAiStreamingChatModel.builder().apiKey("demo").timeout(ofSeconds(30)).build();
        System.out.println("Calling remote chatgpt \uD83E\uDD16 with question:\n" + prompt);

        model.generate(prompt, new StreamingResponseHandler<>() {
            @Override
            public void onNext(String token) {
                System.out.print(token);
                if (token.contains(",") || token.contains(".")) { System.out.println(); }
            }

            @Override
            public void onError(Throwable error) {}
        });

         */
    }

    private void promptLama(String prompt) {
        var model = OllamaStreamingLanguageModel.builder().baseUrl("http://localhost:11434").modelName("llama2")
                .timeout(ofSeconds(30)).build();

        System.out.println("Calling local llama \uD83E\uDD99 with question:\n" + prompt + "\n");

        model.generate(prompt, new StreamingResponseHandler<>() {
            @Override
            public void onNext(String token) {
                System.out.print(token);
                if (token.contains(",") || token.contains(".")) { System.out.println(); }
            }

            @Override
            public void onError(Throwable error) {}
        });
    }

}
