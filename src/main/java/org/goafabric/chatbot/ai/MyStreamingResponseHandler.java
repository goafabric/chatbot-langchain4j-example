package org.goafabric.chatbot.ai;

import dev.langchain4j.model.StreamingResponseHandler;
import dev.langchain4j.model.output.Response;

import java.util.concurrent.CountDownLatch;

public class MyStreamingResponseHandler implements StreamingResponseHandler {
    private final CountDownLatch latch;

    public MyStreamingResponseHandler(CountDownLatch latch) {
        this.latch = latch;
    }

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
    public void onComplete(Response response) {
        StreamingResponseHandler.super.onComplete(response);
        System.out.println();
        latch.countDown();
    }

    @Override
    public void onError(Throwable error) {
    }

    public void await() {
        try {
            latch.await();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
