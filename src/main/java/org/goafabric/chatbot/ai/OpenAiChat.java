package org.goafabric.chatbot.ai;

import dev.langchain4j.model.openai.OpenAiChatModel;

import static java.time.Duration.ofSeconds;

public class OpenAiChat {
    public static void chat(String prompt) {
        System.out.println("Calling remote chatgpt \uD83E\uDD16");

        var model = OpenAiChatModel.builder().apiKey("demo").timeout(ofSeconds(30)).build();
        System.out.println(model.generate(prompt));

        /*
        var model = builder().apiKey("demo").timeout(ofSeconds(30)).build();
        var responseHandler = new MyStreamingResponseHandler(new CountDownLatch(1));
        model.generate(prompt, responseHandler);
        responseHandler.await();
         */

    }

}
