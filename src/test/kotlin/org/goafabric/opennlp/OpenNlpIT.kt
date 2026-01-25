package org.goafabric.opennlp

import opennlp.tools.namefind.NameFinderME
import opennlp.tools.namefind.TokenNameFinderModel
import opennlp.tools.tokenize.SimpleTokenizer
import opennlp.tools.util.Span
import org.junit.jupiter.api.Test
import java.io.IOException
import java.util.*
import java.util.function.Consumer

class OpenNlpIT {
    @Test
    @Throws(IOException::class)
    fun test() {
        val tokenizer = SimpleTokenizer.INSTANCE
        //var text = "John is 26 years old. His best friend's name is Leonard. He has a sister named Penny.";
        val text = "The story of Sheldon Cooper and his boss Monty Burns who lives"

        val tokens = tokenizer.tokenize(text)

        val inputStreamNameFinder = javaClass
            .getResourceAsStream("/models/en-ner-person.bin")
        val model = TokenNameFinderModel(
            inputStreamNameFinder
        )
        val nameFinderME = NameFinderME(model)
        val spans = Arrays.asList<Span?>(*nameFinderME.find(tokens))
        //spans.stream().forEach(span -> span.);
        spans.forEach(Consumer { span: Span? -> println(tokens[span!!.getStart()] + " " + tokens[span.getEnd() - 1]) })
    }
}
