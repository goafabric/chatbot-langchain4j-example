package org.goafabric.dbagentnew.mcp;

import dev.langchain4j.mcp.McpToolProvider;
import dev.langchain4j.mcp.client.DefaultMcpClient;
import dev.langchain4j.mcp.client.McpClient;
import dev.langchain4j.mcp.client.transport.McpTransport;
import dev.langchain4j.mcp.client.transport.stdio.StdioMcpTransport;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.service.tool.ToolProvider;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.util.List;

//https://github.com/modelcontextprotocol/servers/tree/main/src
@Configuration
@Profile("mcp")
public class McpConfiguration {
    @Bean
    public McpBot mcpBot(ChatModel model) {
        McpTransport transport = new StdioMcpTransport.Builder()
                .command(List.of(
                        //"npx", "-y", "@modelcontextprotocol/server-filesystem", "/Users/andreas/Downloads/architecture-decission-records"
                        "npx", "-y", "@modelcontextprotocol/server-postgres", "postgresql://postgres:postgres@localhost:5432/postgres"
                        //"npx", "-y","kubernetes-mcp-server@latest"
                        //"k8sgpt", "serve", "--mcp", "--backend", "ollama"
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
                .chatModel(model)
                .toolProvider(toolProvider)
                .build();
    }

}
