package org.goafabric.llm.vector

import dev.langchain4j.data.segment.TextSegment
import dev.langchain4j.model.embedding.onnx.allminilml6v2.AllMiniLmL6V2EmbeddingModel
import dev.langchain4j.store.embedding.EmbeddingMatch
import dev.langchain4j.store.embedding.EmbeddingSearchRequest
import dev.langchain4j.store.embedding.inmemory.InMemoryEmbeddingStore
import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Profile
import org.springframework.stereotype.Component


//TODO: pgvector
@Component
@Profile("vector")
class VectorSearch {
    private val embeddingStore = InMemoryEmbeddingStore<TextSegment>()
    private val embeddingModel = AllMiniLmL6V2EmbeddingModel()

    data class ChargeItem(var code: String, var display: String, var description: String, var price: Double)

    private fun createChargeItems(): List<ChargeItem> {
        val chargeItem1 = ChargeItem(code = "1", display = "Beratung", "Ärztliche Beratung eines Patienten, auch telefonisch.", price = 4.66)
        val chargeItem2 = ChargeItem(code = "5", display = "Symptombezogene Untersuchung", "Symptombezogene Untersuchung eines Organs oder Organsystems.", price = 4.66)
        val chargeItem3 = ChargeItem(code = "8", display = "Ganzkörperstatus", "Untersuchung zur Erhebung des Ganzkörperstatus, gegebenenfalls einschließlich Dokumentation", price = 15.15)
        val chargeItem4 = ChargeItem(code = "70", display = "Kurzes Gutachten", "Kurzes schriftliches Gutachten oder Attest.", price = 2.33)
        val chargeItem5 = ChargeItem(code = "75", display = "Ausführlicher Befundbericht", "Ausführlicher schriftlicher Krankheits- und Befundbericht", price = 7.55)
        return listOf(chargeItem1 ,chargeItem2, chargeItem3, chargeItem4, chargeItem5)
    }

    @PostConstruct
    fun vectorSearch() {
        createEmbeddings()
        search("Telefonische Beratung")
        search("Internistische Anamese")
    }

    private fun createEmbeddings(
    ) {
        createChargeItems().forEach { charge ->
            val metadata = dev.langchain4j.data.document.Metadata()
            metadata.put("code", charge.code)

            val segment = TextSegment.from(
                "GOÄ ${charge.code}\nTitle: ${charge.display}\nDescription: ${charge.description}", metadata)
            val embedding1 = embeddingModel.embed(segment).content()
            embeddingStore.add(embedding1, segment)
        }
    }

    private fun search(query: String): String? {
        val queryEmbedding = embeddingModel.embed(query).content()
        val embeddingSearchRequest = EmbeddingSearchRequest.builder()
            .queryEmbedding(queryEmbedding)
            .maxResults(2)
            .build()
        val matches: MutableList<EmbeddingMatch<TextSegment>> = embeddingStore.search(embeddingSearchRequest).matches()
        val embeddingMatch: EmbeddingMatch<TextSegment> = matches[0]
        val result = (embeddingMatch.embedded().text())
        //var goaeCode = embeddingMatch.embedded().metadata().getString("code")
        println("Search $query =>\n\n$result\n")
        return result
    }

}