package org.goafabric.myagent.mock;

import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;
import java.util.function.Function;
import java.util.regex.Pattern;

public class MockChatModel implements ChatLanguageModel {
    private final Logger log = LoggerFactory.getLogger(this.getClass().getName());

    private final HashMap<String, Function<String, Object>> functions;

    public MockChatModel(HashMap<String, Function<String, Object>> functions) {
        this.functions = functions;
    }

    @Override
    public Response<AiMessage> generate(List<ChatMessage> messages) {
        return generate(messages, new ArrayList<>());
    }

    @Override
    public Response<AiMessage> generate(List<ChatMessage> messages, ToolSpecification toolSpecification) {
        return generate(messages, Collections.singletonList(toolSpecification));
    }

    @Override
    public Response<AiMessage> generate(List<ChatMessage> messages, List<ToolSpecification> toolSpecifications) {
        var message = messages.get(messages.size() -1 ).text();

        for (Map.Entry<String, Function<String, Object>> entry : functions.entrySet()) {
            var keyWord = entry.getKey();
            var function = entry.getValue();
            if (message.toLowerCase().contains(keyWord.toLowerCase())) {
                try {
                    var parameter = extractValueForKeyword(message, keyWord);
                    return Response.from(new AiMessage(function.apply(parameter).toString()));
                } catch (Exception e) {
                    log.error(e.getMessage());
                    return Response.from(new AiMessage("sorry, there was an error calling the function"));
                }
            }
        }

        return Response.from(new AiMessage("sorry, i am just a stupid mock bot, i cannot help you with that"));
    }

    private static String extractValueForKeyword(String inputString, String keyword) {
        var pattern = Pattern.compile("\\b" + keyword + "\\s+([^\\s]+)");
        var matcher = pattern.matcher(inputString);

        if (matcher.find()) {
            return matcher.find() ? matcher.group(1).trim() : throw new IllegalArgumentException("keyword not found");
        } else {
            throw new IllegalArgumentException("keyword not found");
        }
    }
}
