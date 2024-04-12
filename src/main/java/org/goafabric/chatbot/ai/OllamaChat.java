package org.goafabric.chatbot.ai;

import dev.langchain4j.model.ollama.OllamaStreamingLanguageModel;

import java.util.concurrent.CountDownLatch;

import static java.time.Duration.ofSeconds;

public class OllamaChat {

    public static void chat(String prompt) {
        System.out.println("Calling local llama \uD83E\uDD99");

        var model = OllamaStreamingLanguageModel.builder().baseUrl("http://localhost:11434")
                .modelName("llama2")
                .timeout(ofSeconds(30)).build();

        var responseHandler = new MyStreamingResponseHandler(new CountDownLatch(1));
        model.generate(prompt, responseHandler);
        responseHandler.await();
    }


}
