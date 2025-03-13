package org.goafabric.dbagentnew;

import org.goafabric.dbagentnew.ai.DatabaseAgent;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.aot.hint.annotation.RegisterReflection;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@Configuration
@ImportRuntimeHints(NativeConfiguration.ApplicationRuntimeHints.class)
@RegisterReflection(classNames = {
        "dev.ai4j.openai4j.chat.ChatCompletionResponse$Builder",
        "dev.ai4j.openai4j.shared.Usage$Builder",
        "dev.ai4j.openai4j.shared.CompletionTokensDetails$Builder",
        "dev.ai4j.openai4j.shared.PromptTokensDetails$Builder",
        "dev.ai4j.openai4j.chat.ChatCompletionChoice$Builder",
        "dev.ai4j.openai4j.chat.Delta$Builder$Builder",
        "dev.ai4j.openai4j.chat.Function$Builder",
        "dev.ai4j.openai4j.chat.FunctionCall$Builder",
        "dev.ai4j.openai4j.chat.Tool$Builder",
        "dev.ai4j.openai4j.chat.ToolCall$Builder",
        "dev.ai4j.openai4j.chat.AssistantMessage$Builder",
        "dev.ai4j.openai4j.chat.Content$Builder",

        "org.goafabric.dbagentnew.logic.PersonLogic"},

        memberCategories = {MemberCategory.INVOKE_DECLARED_CONSTRUCTORS, MemberCategory.INVOKE_DECLARED_METHODS}
)
public class NativeConfiguration {
    static class ApplicationRuntimeHints implements RuntimeHintsRegistrar {
        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            hints.proxies().registerJdkProxy(DatabaseAgent.class);
        }
    }
}
