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
import org.tribuo.common.nearest.KNNModel
import org.tribuo.common.nearest.KNNTrainer
import org.tribuo.data.csv.CSVLoader
import org.tribuo.evaluation.TrainTestSplitter
import org.tribuo.impl.ArrayExample
import org.tribuo.math.distance.DistanceType
import org.tribuo.math.neighbour.NeighboursQueryFactoryType

/**
 * Tribuo port of the scikit-learn tv price example:
 * read csv -> train/test split -> KNeighborsClassifier(1) -> score -> predict.
 */
@Component
@Profile("ml-tv")
class TvPrice {

    private val labelFactory = LabelFactory()

    @PostConstruct
    fun init() {
        val data = readFile()
        val classifier = trainMe(data)

        println("predict 4599: " + predict(classifier, month = 14.0, initial = 4599.0))
        println("predict 2599: " + predict(classifier, month = 14.0, initial = 2599.0))
        println("predict 620: " + predict(classifier, month = 6.0, initial = 620.0))
    }

    private fun readFile(): DataSource<Label> =
        CSVLoader(labelFactory).loadDataSource(
            ClassPathResource("ml/tv_price_fake.csv").filePath,
            "price" /* response / target column, 'month' and 'initial' are the features */
        )

    private fun trainMe(source: DataSource<Label>): Model<Label> {
        // train_test_split(values, target, test_size=0.4)
        val splitter = TrainTestSplitter(source, /* train fraction */ 0.6, /* RNG seed */ 1L)
        val trainData = MutableDataset(splitter.train)
        val testData = MutableDataset(splitter.test)

        // neighbors.KNeighborsClassifier(1) + clf.fit(...)
        val trainer = KNNTrainer<Label>(
            /* k */ 1,
            DistanceType.L2.distance,
            /* numThreads */ 1,
            VotingCombiner(),
            KNNModel.Backend.THREADPOOL,
            NeighboursQueryFactoryType.BRUTE_FORCE
        )
        val model = trainer.train(trainData)

        // clf.score(...)
        println("normal train score " + score(model, trainData))
        println("normal test score " + score(model, testData))
        return model
    }

    private fun score(model: Model<Label>, dataset: Dataset<Label>): Double =
        dataset.count { model.predict(it).output.label == it.output.label }.toDouble() / dataset.size()

    private fun predict(model: Model<Label>, month: Double, initial: Double): String {
        val example = ArrayExample(
            labelFactory.unknownOutput,
            arrayOf("month", "initial"),
            doubleArrayOf(month, initial)
        )
        return model.predict(example).output.label
    }
}
