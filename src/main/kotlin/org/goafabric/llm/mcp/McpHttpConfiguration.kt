package org.goafabric.llm.mcp

import dev.langchain4j.mcp.McpToolProvider
import dev.langchain4j.mcp.client.DefaultMcpClient
import dev.langchain4j.mcp.client.McpClient
import dev.langchain4j.mcp.client.transport.http.StreamableHttpMcpTransport
import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.service.AiServices
import dev.langchain4j.service.tool.ToolProvider
import org.goafabric.llm.config.Assistant
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.util.List

//https://github.com/modelcontextprotocol/servers/tree/main/src
@Configuration
@Profile("mcp-http")
class McpHttpConfiguration {
    @Bean
    fun mcpBot(model: ChatModel?): Assistant? {
        val transport = StreamableHttpMcpTransport.Builder()
            .url("http://localhost:50800/mcp")
            .build()

        val mcpClient: McpClient = DefaultMcpClient.Builder()
            .transport(transport)
            .build()

        val toolProvider: ToolProvider? = McpToolProvider.builder()
            .mcpClients(List.of<McpClient?>(mcpClient))
            .build()

        return AiServices.builder<Assistant?>(Assistant::class.java)
            .chatModel(model)
            .toolProvider(toolProvider)
            .build()
    }
}
