package org.apache.spark.mllib.tree

import org.apache.spark.SparkContext
import org.apache.spark.SparkConf

import org.apache.spark.mllib.linalg.Vectors
import org.apache.spark.mllib.regression.LabeledPoint
import org.apache.spark.mllib.tree.model.DecisionTreeModel

import scala.util.Random

import org.apache.log4j.Logger
import org.apache.log4j.Level

import org.scalatest.FunSuite

class ReducerTest extends FunSuite {

	Logger.getLogger("org").setLevel(Level.OFF)
	Logger.getLogger("akka").setLevel(Level.OFF)

	test("Merge Naive Leafs") {

		val naiveDecisionTreeModel = buildTestDecisionTreeModel()

		val reducedDecisionTreeModel = Reducer.mergeLeafs(naiveDecisionTreeModel)

		assert(reducedDecisionTreeModel.toDebugString.trim === StringifiedTrees.MERGED_LEAFS_TREE)
	}

	/** Generates a DecisionTreeModel object */
	private def buildTestDecisionTreeModel(): DecisionTreeModel = {

		val sparkContext = new SparkContext(
			new SparkConf().setAppName("Spark").setMaster("local[2]")
		)

		val labeledPoints = sparkContext.parallelize(
			generateDeterministRandomDataPoints()
		)

		val decisionTreeModel = DecisionTree.trainClassifier(
			input = labeledPoints,
			numClasses = 3,
			categoricalFeaturesInfo = Map(0 -> 7, 1 -> 7),
			impurity = "gini",
			maxDepth = 5,
			maxBins = 10
		)

		sparkContext.stop()

		assert(decisionTreeModel.toDebugString.trim === StringifiedTrees.INITIAL_TREE)

		decisionTreeModel
	}

	/** Generates a set of labeled data points in a deterministic way */
	private def generateDeterministRandomDataPoints(): Array[LabeledPoint] = {

		val random = new Random()
		random.setSeed(1000L)

		val points = new Array[LabeledPoint](3000)

		for (i <- 0 until 3000) {

			val label =  if (i < 1500) 0.0 else if (i < 2000) 1.0 else 2.0

			points(i) = new LabeledPoint(
				label,
				Vectors.dense(Array(
					random.nextInt(7).toDouble,
					random.nextInt(7).toDouble,
					800d + random.nextFloat.toDouble * 6000d,
					random.nextInt(250).toDouble,
					random.nextInt(250).toDouble,
					random.nextInt(10).toDouble
				))
			)
		}

		points
	}
}
