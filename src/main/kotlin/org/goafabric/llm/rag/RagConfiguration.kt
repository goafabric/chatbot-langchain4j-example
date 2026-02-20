package org.goafabric.llm.rag

import com.zaxxer.hikari.HikariDataSource
import dev.langchain4j.data.document.loader.FileSystemDocumentLoader
import dev.langchain4j.data.document.parser.TextDocumentParser
import dev.langchain4j.data.document.parser.apache.tika.ApacheTikaDocumentParser
import dev.langchain4j.data.document.splitter.DocumentSplitters
import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.memory.chat.MessageWindowChatMemory
import dev.langchain4j.model.chat.ChatModel
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel
import dev.langchain4j.rag.content.retriever.ContentRetriever
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever
import dev.langchain4j.service.AiServices
import dev.langchain4j.store.embedding.EmbeddingStore
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore
import dev.langchain4j.store.embedding.pgvector.PgVectorEmbeddingStore
import org.goafabric.llm.config.Assistant
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Profile
import java.net.URISyntaxException
import java.nio.file.Path
import java.nio.file.Paths
import java.util.function.Consumer

@Configuration
@Profile("rag")
class RagConfiguration(private val dataSource: HikariDataSource) {

    @Bean
    fun ragBot(chatModel: ChatModel?): Assistant? {
        val textSegments = createTexSegments(
            mutableListOf( "doc/story-about-happy-carrot.pdf"
                //"doc/biography-of-john-doe.txt"
            )
        )

        //var textSegments =  Collections.singletonList(TextSegment.from("Hello World"));
        val embeddingModel = BgeSmallEnV15QuantizedEmbeddingModel()
        val embeddingStore = creteEmbedding(embeddingModel, textSegments)
        val contentRetriever = cretateContentRetriever(embeddingStore, embeddingModel)

        return AiServices.builder<Assistant?>(Assistant::class.java)
            .chatModel(chatModel)
            .contentRetriever(contentRetriever)
            .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
            .build()
    }

    private fun createTexSegments(fileNames: MutableList<String?>): MutableList<TextSegment?> {
        val textSegments = ArrayList<TextSegment?>()
        fileNames.forEach(Consumer { fileName: String? ->
            val path: Path = toPath(fileName)
            val document = if (fileName!!.contains(".pdf"))
                FileSystemDocumentLoader.loadDocument(path, ApacheTikaDocumentParser())
            else
                FileSystemDocumentLoader.loadDocument(path, TextDocumentParser())
            textSegments.addAll(DocumentSplitters.recursive(300, 0).split(document))
        })
        return textSegments
        //return Collections.singletonList(TextSegment.from("Hello World"));
    }

    private fun creteEmbedding(
        embeddingModel: BgeSmallEnV15QuantizedEmbeddingModel,
        textSegments: MutableList<TextSegment?>?
    ): EmbeddingStore<TextSegment?> {
        val embeddingStore = if (dataSource.driverClassName == "org.postgresql.Driver")
            PgVectorEmbeddingStore.datasourceBuilder().datasource(dataSource).table("my_vector")
                .dimension(embeddingModel.dimension()).build()
        else
            InMemoryEmbeddingStore<TextSegment?>()

        embeddingStore.addAll(
            embeddingModel.embedAll(textSegments).content(), textSegments
        )
        return embeddingStore
    }


    private fun cretateContentRetriever(
        embeddingStore: EmbeddingStore<TextSegment?>?,
        embeddingModel: BgeSmallEnV15QuantizedEmbeddingModel?
    ): ContentRetriever? {
        return EmbeddingStoreContentRetriever.builder()
            .embeddingStore(embeddingStore)
            .embeddingModel(embeddingModel)
            .maxResults(2) // on each interaction we will retrieve the 2 most relevant segments
            .minScore(0.5) // we want to retrieve segments at least somewhat similar to user query
            .build()
    }

    companion object {
        private fun toPath(relativePath: String?): Path {
            try {
                val fileUrl = RagConfiguration::class.java.getClassLoader().getResource(relativePath)
                return Paths.get(fileUrl!!.toURI())
            } catch (e: URISyntaxException) {
                throw RuntimeException(e)
            }
        }
    }
}
