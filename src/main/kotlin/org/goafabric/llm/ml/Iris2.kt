package org.goafabric.llm.ml

import jakarta.annotation.PostConstruct
import org.springframework.context.annotation.Profile
import org.springframework.core.io.ClassPathResource
import org.springframework.stereotype.Component
import org.tribuo.Dataset
import org.tribuo.Example
import org.tribuo.Model
import org.tribuo.MutableDataset
import org.tribuo.Trainer
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
 * Tribuo port of the scikit-learn iris knn example.
 *
 * sklearn ships the dataset inside the library (datasets.load_iris()), Tribuo does not, so the very
 * same UCI data is read from src/main/resources/ml/bezdekIris.data. The file has no header row,
 * therefore the column names are handed over explicitly. 'species' is the target (y), the four
 * measurements are the input matrix (X).
 */
@Component
@Profile("ml-iris2")
class Iris2 {

    private val labelFactory = LabelFactory()

    private val headers = arrayOf("sepalLength", "sepalWidth", "petalLength", "petalWidth", "species")
    private val sepalFeatures = arrayOf("sepalLength", "sepalWidth")
    private val petalFeatures = arrayOf("petalLength", "petalWidth")

    @PostConstruct
    fun init() {
        // split iris data into 60% training and 40% test data
        val splitter = TrainTestSplitter(loadIris(), /* train fraction */ 0.6, /* RNG seed */ 1L)
        val trainData = MutableDataset(splitter.train)
        val testData = MutableDataset(splitter.test)

        val clf = trainNormal(trainData)

        predictConcreteExample(clf)
        printNormalScores(clf, trainData, testData)

        trainingSepalOnly(trainData, testData)
        trainingPetalOnly(trainData, testData)
    }

    /** datasets.load_iris() */
    private fun loadIris() =
        CSVLoader(labelFactory).loadDataSource(
            ClassPathResource("ml/bezdekIris.data").filePath,
            /* response column (y) */ headers[4],
            /* the file carries no header row */ headers
        )

    /** nearest neighbour estimator + clf.fit(X_train, y_train) */
    private fun trainNormal(trainData: Dataset<Label>): Model<Label> = knn(1).train(trainData)

    /** clf.predict([[6.3, 2.7, 5.5, 1.5]]) */
    private fun predictConcreteExample(clf: Model<Label>) {
        val example = ArrayExample(
            labelFactory.unknownOutput,
            headers.copyOf(4), // the four measurements, without the target column
            doubleArrayOf(6.3, 2.7, 5.5, 1.5)
        )
        println("predict " + clf.predict(example).output.label)
        println()
    }

    private fun printNormalScores(clf: Model<Label>, trainData: Dataset<Label>, testData: Dataset<Label>) {
        println("normal train score " + score(clf, trainData))
        println("normal test score " + score(clf, testData))
        println()
    }

    private fun trainingSepalOnly(trainData: Dataset<Label>, testData: Dataset<Label>) {
        val trainSepalOnly = onlyFeatures(trainData, *sepalFeatures)
        val testSepalOnly = onlyFeatures(testData, *sepalFeatures)

        val clfSepal10 = knn(10).train(trainSepalOnly)
        println("sepal only train score:  " + score(clfSepal10, trainSepalOnly))
        println("sepal only test score:  " + score(clfSepal10, testSepalOnly))
        println()
    }

    private fun trainingPetalOnly(trainData: Dataset<Label>, testData: Dataset<Label>) {
        val trainPetalOnly = onlyFeatures(trainData, *petalFeatures)
        val testPetalOnly = onlyFeatures(testData, *petalFeatures)

        val clfPetal10 = knn(10).train(trainPetalOnly)
        println("petal only train score:  " + score(clfPetal10, trainPetalOnly))
        println("petal only test score:  " + score(clfPetal10, testPetalOnly))
    }

    /** neighbors.KNeighborsClassifier(k) */
    private fun knn(k: Int): Trainer<Label> = KNNTrainer(
        k,
        DistanceType.L2.distance,
        /* numThreads */ 1,
        VotingCombiner(),
        KNNModel.Backend.THREADPOOL,
        NeighboursQueryFactoryType.BRUTE_FORCE
    )

    /** clf.score(X, y) */
    private fun score(model: Model<Label>, dataset: Dataset<Label>): Double =
        LabelEvaluator().evaluate(model, dataset).accuracy()

    /** the numpy column slicing, e.g. X_train[:, :2] resp. X_train[:, 2:] */
    private fun onlyFeatures(dataset: Dataset<Label>, vararg featureNames: String): Dataset<Label> {
        val keep = featureNames.toSet()
        val examples = dataset.map { example ->
            ArrayExample(example.output, example.filter { keep.contains(it.name) }) as Example<Label>
        }
        return MutableDataset(examples, dataset.provenance, labelFactory)
    }
}
