package org.goafabric.llm.ml

import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import org.tribuo.DataSource
import org.tribuo.Dataset
import org.tribuo.Model
import org.tribuo.MutableDataset
import org.tribuo.classification.Label
import org.tribuo.classification.LabelFactory
import org.tribuo.classification.ensemble.VotingCombiner
import org.tribuo.classification.evaluation.LabelEvaluator
import org.tribuo.common.nearest.KNNModel
import org.tribuo.common.nearest.KNNTrainer
import org.tribuo.data.csv.CSVLoader
import org.tribuo.evaluation.TrainTestSplitter
import org.tribuo.impl.ArrayExample
import org.tribuo.math.distance.DistanceType
import org.tribuo.math.neighbour.NeighboursQueryFactoryType

/**
 * Tribuo port of the scikit-learn body size example:
 * read csv -> train/test split -> KNeighborsClassifier(1) -> score -> predict.
 */
@Component
@Profile("ml-body")
class BodySize {

    private val labelFactory = LabelFactory()

    @PostConstruct
    fun init() {
        scoreMe(readFile())

        // the scoring model above only sees 60% of the 15 rows, so a 1-NN lookup of a size that was
        // split away ends in a coin flip (e.g. 140 is equally far from 130 child and 150 adult).
        // for the real prediction we therefore fit on the complete dataset.
        val classifier = trainMe(MutableDataset(readFile()))

        println("predict: " + predict(classifier, 140.0))
    }

    private fun readFile(): DataSource<Label> =
        CSVLoader(labelFactory).loadDataSource(
            ClassPathResource("ml/body_size.csv").filePath,
            "type" /* response / target column, the remaining column 'size' is the feature */
        )

    /** neighbors.KNeighborsClassifier(1) + clf.fit(...) */
    private fun trainMe(trainData: Dataset<Label>): Model<Label> {
        val trainer = KNNTrainer<Label>(
            /* k */ 1,
            DistanceType.L2.distance,
             1,
            VotingCombiner(),
            KNNModel.Backend.THREADPOOL,
            NeighboursQueryFactoryType.BRUTE_FORCE
        )
        return trainer.train(trainData)
    }

    private fun scoreMe(source: DataSource<Label>) {
        // train_test_split(values, target, test_size=0.4)
        val splitter = TrainTestSplitter(source, /* train fraction */ 0.6, /* RNG seed */ 1L)
        val trainData = MutableDataset(splitter.train)
        val testData = MutableDataset(splitter.test)

        val model = trainMe(trainData)

        // clf.score(...)
        println("normal train score " + score(model, trainData))
        println("normal test score " + score(model, testData))
    }

    private fun score(model: Model<Label>, dataset: Dataset<Label>): Double =
        LabelEvaluator().evaluate(model, dataset).accuracy()

    private fun predict(model: Model<Label>, size: Double): String {
        val example = ArrayExample(labelFactory.unknownOutput, arrayOf("size"), doubleArrayOf(size))
        return model.predict(example).output.label
    }
}
