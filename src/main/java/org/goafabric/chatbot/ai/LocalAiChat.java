package org.goafabric.chatbot.ai;

import dev.langchain4j.model.localai.LocalAiLanguageModel;

import static java.time.Duration.ofSeconds;

public class LocalAiChat {
    public static void chat(String prompt) {
        System.out.println("Calling localai \uD83E\uDD16");

        var model2 = LocalAiLanguageModel.builder().modelName("GPT4All-J Groovy.bin").baseUrl("http://localhost:8000/v1").timeout(ofSeconds(30)).build();
        model2.generate(prompt);

        /*
        var model = builder().apiKey("demo").timeout(ofSeconds(30)).build();
        var responseHandler = new MyStreamingResponseHandler(new CountDownLatch(1));
        model.generate(prompt, responseHandler);
        responseHandler.await();
         */

    }

}
