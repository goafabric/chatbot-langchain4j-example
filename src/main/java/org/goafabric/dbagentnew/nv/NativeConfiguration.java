package org.goafabric.dbagentnew.nv;

import org.goafabric.dbagentnew.ai.DatabaseAgent;
import org.goafabric.dbagentnew.logic.PersonLogic;
import org.springframework.aot.hint.MemberCategory;
import org.springframework.aot.hint.RuntimeHints;
import org.springframework.aot.hint.RuntimeHintsRegistrar;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.ImportRuntimeHints;

@Configuration
@ImportRuntimeHints(NativeConfiguration.ApplicationRuntimeHints.class)
public class NativeConfiguration {
    static class ApplicationRuntimeHints implements RuntimeHintsRegistrar {
        @Override
        public void registerHints(RuntimeHints hints, ClassLoader classLoader) {
            try {
                hints.proxies().registerJdkProxy(DatabaseAgent.class);
                hints.reflection().registerType(Class.forName("dev.ai4j.openai4j.chat.ChatCompletionResponse$Builder"),
                        MemberCategory.DECLARED_CLASSES, MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
                hints.reflection().registerType(Class.forName("dev.ai4j.openai4j.shared.Usage$Builder"),
                        MemberCategory.DECLARED_CLASSES, MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
                hints.reflection().registerType(Class.forName("dev.ai4j.openai4j.shared.CompletionTokensDetails$Builder"),
                        MemberCategory.DECLARED_CLASSES, MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
                hints.reflection().registerType(Class.forName("dev.ai4j.openai4j.shared.PromptTokensDetails$Builder"),
                        MemberCategory.DECLARED_CLASSES, MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
                hints.reflection().registerType(Class.forName("dev.ai4j.openai4j.chat.ChatCompletionChoice$Builder"),
                        MemberCategory.DECLARED_CLASSES, MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
                hints.reflection().registerType(Class.forName("dev.ai4j.openai4j.chat.Delta$Builder"),
                        MemberCategory.DECLARED_CLASSES, MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
                hints.reflection().registerType(Class.forName("dev.ai4j.openai4j.chat.Function$Builder"),
                        MemberCategory.DECLARED_CLASSES, MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
                hints.reflection().registerType(Class.forName("dev.ai4j.openai4j.chat.FunctionCall$Builder"),
                        MemberCategory.DECLARED_CLASSES, MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
                hints.reflection().registerType(Class.forName("dev.ai4j.openai4j.chat.Tool$Builder"),
                        MemberCategory.DECLARED_CLASSES, MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
                hints.reflection().registerType(Class.forName("dev.ai4j.openai4j.chat.ToolCall$Builder"),
                        MemberCategory.DECLARED_CLASSES, MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);

                hints.reflection().registerType(Class.forName("dev.ai4j.openai4j.chat.AssistantMessage$Builder"),
                        MemberCategory.DECLARED_CLASSES, MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
                hints.reflection().registerType(Class.forName("dev.ai4j.openai4j.chat.Delta$Builder"),
                        MemberCategory.DECLARED_CLASSES, MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);
                hints.reflection().registerType(Class.forName("dev.ai4j.openai4j.chat.Content$Builder"),
                        MemberCategory.DECLARED_CLASSES, MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);

                hints.reflection().registerType(PersonLogic.class,
                        MemberCategory.DECLARED_CLASSES, MemberCategory.INVOKE_DECLARED_METHODS, MemberCategory.INVOKE_DECLARED_CONSTRUCTORS);


            } catch (ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
