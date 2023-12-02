package org.goafabric.chatbot.ai;

import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.ollama.OllamaStreamingLanguageModel;

import static java.time.Duration.ofSeconds;

public class OllamaChat {

    private static void chat(String prompt) {
        var model = OllamaStreamingLanguageModel.builder().baseUrl("http://localhost:11434").modelName("llama2")
                .timeout(ofSeconds(30)).build();

        System.out.println("Calling local llama \uD83E\uDD99");

        model.generate(prompt, new StreamingResponseHandler<>() {
            @Override
            public void onNext(String token) {
                if (token != null) {
                    System.out.print(token);
                    if (token.contains(",") || token.contains(".")) {
                        System.out.println();
                    }
                }
            }

            @Override
            public void onError(Throwable error) {}
        });
    }
}
