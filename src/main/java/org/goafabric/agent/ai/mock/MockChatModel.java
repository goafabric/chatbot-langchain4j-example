package org.goafabric.agent.ai.mock;

import dev.langchain4j.agent.tool.ToolSpecification;
import dev.langchain4j.data.message.AiMessage;
import dev.langchain4j.data.message.ChatMessage;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.model.output.Response;
import org.jetbrains.annotations.NotNull;
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
        var message = messages.get(messages.size() -1 ).text(); //get the last message
        return parseMessage(message);
    }

    @NotNull
    private Response<AiMessage> parseMessage(String message) {
        return functions.entrySet().stream()
                .filter(entry -> message.toLowerCase().contains(entry.getKey().toLowerCase()))
                .findFirst()
                .map(entry -> {
                    try {
                        var function = entry.getValue();
                        var parameter = extractValueForKeyword(message, entry.getKey());
                        return Response.from(new AiMessage("I have found: " + function.apply(parameter).toString()));
                    } catch (Exception e) {
                        log.error(e.getMessage(), e);
                        return Response.from(new AiMessage("Sorry, there was an error calling the function"));
                    }
                }).orElse(Response.from(new AiMessage("I am sorry, but i cannot help you with that. I am just a simple mock.")));
    }

    private String extractValueForKeyword(String inputString, String keyword) {
        var pattern = Pattern.compile("\\b" + keyword + "\\s+([^\\s]+)", Pattern.CASE_INSENSITIVE);
        var matcher = pattern.matcher(inputString);
        if (matcher.find()) {
            return matcher.group(1).trim();
        } else {
            throw new IllegalArgumentException("keyword not found");
        }
    }
}
