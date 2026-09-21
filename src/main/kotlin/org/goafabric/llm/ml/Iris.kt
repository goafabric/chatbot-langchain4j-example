package org.goafabric.llm.ml

import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import org.tribuo.*
import org.tribuo.classification.Label
import org.tribuo.classification.LabelFactory
import org.tribuo.classification.dtree.CARTClassificationTrainer
import org.tribuo.classification.evaluation.LabelEvaluation
import org.tribuo.classification.evaluation.LabelEvaluator
import org.tribuo.classification.sgd.linear.LogisticRegressionTrainer
import org.tribuo.data.csv.CSVLoader
import org.tribuo.evaluation.TrainTestSplitter
import java.nio.file.Paths



@Component
@Profile("ml")
class Iris {

    @PostConstruct
    fun init() {

        // Load labelled iris data
        val irisHeaders: Array<String> =
            arrayOf<String>("sepalLength", "sepalWidth", "petalLength", "petalWidth", "species")

        val irisData: DataSource<Label?>? = CSVLoader(LabelFactory()).loadDataSource(
            ClassPathResource("ml/bezdekIris.data").filePath,
            irisHeaders[4],  /* Column headers  */
            irisHeaders
        )


        // Split iris data into training set (70%) and test set (30%)
        val splitIrisData = TrainTestSplitter(
            irisData,  /* Train fraction */
            0.7,  /* RNG seed */
            1L
        )
        val trainData = MutableDataset(splitIrisData.getTrain())
        val testData = MutableDataset(splitIrisData.getTest())


        // We can train a decision tree
        val cartTrainer: CARTClassificationTrainer = CARTClassificationTrainer()
        val tree: Model<Label?>? = cartTrainer.train(trainData)

        // Or a logistic regression
        val linearTrainer: LogisticRegressionTrainer = LogisticRegressionTrainer()
        val linear: Model<Label?> = linearTrainer.train(trainData)


        // Finally we make predictions on unseen data
        // Each prediction is a map from the output names (i.e. the labels) to the scores/probabilities
        val prediction: Prediction<Label?>? = linear.predict(testData.getExample(0))
        
        // Or we can evaluate the full test dataset, calculating the accuracy, F1 etc.
        val evaluation: LabelEvaluation = LabelEvaluator().evaluate(linear, testData)

        // we can inspect the evaluation manually
        val acc: Double = evaluation.accuracy()


        // which returns 0.978
        // or print a formatted evaluation string
        println(evaluation.toString())

    }
}