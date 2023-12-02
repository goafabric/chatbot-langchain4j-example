package org.goafabric.chatbot.ai;

import dev.langchain4j.model.openai.OpenAiChatModel;

import static java.time.Duration.ofSeconds;

public class OpenAiChat {
    public static void chat(String prompt) {
        var model = OpenAiChatModel.builder().apiKey("demo").timeout(ofSeconds(30)).build();
        System.out.println("Calling remote chatgpt \uD83E\uDD16");
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

}
