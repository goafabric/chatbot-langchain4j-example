package org.goafabric.dbagentnew.rag;

import dev.langchain4j.data.document.Document;
import dev.langchain4j.data.document.DocumentParser;
import dev.langchain4j.data.document.DocumentSplitter;
import dev.langchain4j.data.document.parser.TextDocumentParser;
import dev.langchain4j.data.document.splitter.DocumentSplitters;
import dev.langchain4j.data.segment.TextSegment;
import dev.langchain4j.memory.chat.MessageWindowChatMemory;
import dev.langchain4j.model.chat.ChatModel;
import dev.langchain4j.model.embedding.onnx.bgesmallenv15q.BgeSmallEnV15QuantizedEmbeddingModel;
import dev.langchain4j.rag.content.retriever.ContentRetriever;
import dev.langchain4j.rag.content.retriever.EmbeddingStoreContentRetriever;
import dev.langchain4j.service.AiServices;
import dev.langchain4j.store.embedding.EmbeddingStore;
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore;
import org.jspecify.annotations.NonNull;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import java.net.URISyntaxException;
import java.net.URL;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

import static dev.langchain4j.data.document.loader.FileSystemDocumentLoader.loadDocument;

@Configuration
@Profile("rag")
public class RagConfiguration {
    @Bean
    public RagBot ragBot(ChatModel chatModel) {
        List<TextSegment> segments = createDocumentSegments();

        var embeddingModel = new BgeSmallEnV15QuantizedEmbeddingModel();
        var embeddingStore = creteEmbedding(embeddingModel, segments);
        var contentRetriever = cretateContentRetriever(embeddingStore, embeddingModel);

        return AiServices.builder(RagBot.class)
                .chatModel(chatModel)
                .contentRetriever(contentRetriever)
                .chatMemory(MessageWindowChatMemory.withMaxMessages(10))
                .build();
        
    }

    private static List<TextSegment> createDocumentSegments() {
        Path path = toPath("doc/biography-of-john-doe.txt");
        DocumentParser documentParser = new TextDocumentParser();
        Document document = loadDocument(path, documentParser);

        DocumentSplitter splitter = DocumentSplitters.recursive(300, 0);
        return splitter.split(document);
    }

    private static @NonNull EmbeddingStore<TextSegment> creteEmbedding(BgeSmallEnV15QuantizedEmbeddingModel embeddingModel, List<TextSegment> segments) {
        var embeddings = embeddingModel.embedAll(segments).content();

        EmbeddingStore<TextSegment> embeddingStore = new InMemoryEmbeddingStore<>();
        embeddingStore.addAll(embeddings, segments);
        return embeddingStore;
    }


    private static ContentRetriever cretateContentRetriever(EmbeddingStore<TextSegment> embeddingStore, BgeSmallEnV15QuantizedEmbeddingModel embeddingModel) {
        return EmbeddingStoreContentRetriever.builder()
                .embeddingStore(embeddingStore)
                .embeddingModel(embeddingModel)
                .maxResults(2) // on each interaction we will retrieve the 2 most relevant segments
                .minScore(0.5) // we want to retrieve segments at least somewhat similar to user query
                .build();
    }

    private static Path toPath(String relativePath) {
        try {
            URL fileUrl = RagConfiguration.class.getClassLoader().getResource(relativePath);
            return Paths.get(fileUrl.toURI());
        } catch (URISyntaxException e) {
            throw new RuntimeException(e);
        }
    }

}
