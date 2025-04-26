package org.goafabric.dbagentnew.mcp;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.stdio.StdioMcpTransport;
import dev.langchain4j.model.chat.ChatLanguageModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class McpConfiguration {
    @Bean
    public McpBot mcpBot(ChatLanguageModel model) {
        McpTransport transport = new StdioMcpTransport.Builder()
                .command(List.of(
                        "npx",
                        "-y",
                        //"@modelcontextprotocol/server-filesystem", "/Users/andreas/Downloads/architecture-decission-records"
                        "@modelcontextprotocol/server-postgres", "postgres://postgres:postgres@localhost:5432/postgres"
                ))
                .logEvents(true)
                .build();

        McpClient mcpClient = new DefaultMcpClient.Builder()
                .transport(transport)
                .build();

        ToolProvider toolProvider = McpToolProvider.builder()
                .mcpClients(List.of(mcpClient))
                .build();

        return AiServices.builder(McpBot.class)
                .chatLanguageModel(model)
                .toolProvider(toolProvider)
                .build();
    }

}
