package org.apache.spark.mllib.tree

import org.apache.spark.mllib.tree.model.Predict
import org.apache.spark.mllib.tree.model.Node
import org.apache.spark.mllib.tree.model.DecisionTreeModel

/** Simplifies a bit a naive mllib decision tree.
  *
  * Decision trees as produced by mllib often have branches with two leafs
  * predicting the same label:
  *
  * {{{
  * """
  * If (feature 5 > 8.0)
  *  If (feature 2 <= 4473.989772796631)
  *   If (feature 0 in {4.0})
  *    Predict: 0.0
  *   Else (feature 0 not in {4.0})
  *    Predict: 0.0
  *  Else (feature 2 > 4473.989772796631)
  *   If (feature 3 <= 126.0)
  *    Predict: 0.0
  *   Else (feature 3 > 126.0)
  *    Predict: 2.0
  * """
  * }}}
  *
  * Notice how the two first leafs for feature 0 predict the same label. The aim
  * of this code snipet is to merge these naive rules in order to produce a
  * simplified/minimized DecisionTreeModel object as follows:
  *
  * {{{
  * """
  * If (feature 5 > 8.0)
  *  If (feature 2 <= 4473.989772796631)
  *   Predict: 0.0
  *  Else (feature 2 > 4473.989772796631)
  *   If (feature 3 <= 126.0)
  *    Predict: 0.0
  *   Else (feature 3 > 126.0)
  *    Predict: 2.0
  * """
  * }}}
  *
  * The merge of leafs is an iterative procedure, since new merged leafs at
  * depth n-1 can also be merged.
  *
  * This reduction is usefull when the decision tree is then used at scale, and
  * when performances matter. Less conditions means faster execution time.
  *
  * This is also usefull when the decision tree is to be serialized as a set of
  * rules instead of a tree. In this case this reduction is the first step of
  * simplication.
  *
  * It's particularly usefull for trees with less labels to predict.
  *
  * Source <a href="https://github.com/xavierguihot/mllib_decision_tree_reducer/blob/master/src/main/scala/org/apache/spark/mllib/tree/Reducer.scala">Reducer</a>
  *
  * @author Xavier Guihot
  * @since 2017-06
  */
object Reducer {

	/** Reduces/simplifies an mllib decision tree.
	  *
	  * For instance for a decision tree which contains these nodes:
	  *
	  * {{{
	  * """
	  * If (feature 1 in {1.0,2.0,4.0,5.0,0.0})
      *  If (feature 5 <= 0.0)
      *   If (feature 4 <= 71.0)
      *    Predict: 0.0
      *   Else (feature 4 > 71.0)
      *    Predict: 0.0
      *  Else (feature 5 > 0.0)
      *   If (feature 4 <= 23.0)
      *    Predict: 0.0
      *   Else (feature 4 > 23.0)
      *    Predict: 0.0
      * """
	  * }}}
	  *
	  * this method would reduce the tree in order to obtain these nodes instead:
	  *
	  * {{{
	  * """
	  * If (feature 1 in {1.0,2.0,4.0,5.0,0.0})
      *  If (feature 5 <= 0.0)
      *   Predict: 0.0
      * """
	  * }}}
	  *
	  * @param decisionTreeModel the mllib DecisionTreeModel object to be
	  * reduced.
	  * @return the reduced DecisionTreeModel. The decision tree given as
	  * parameter will also be modified during this process.
	  */
	def mergeLeafs(decisionTreeModel: DecisionTreeModel): DecisionTreeModel = {

		var didMergeTwoLeafs = true

		// This is an iteratif step. We begin with leafs at the deepest layer,
		// and we continue in direction of the root up to the layer for which no
		// leafs could be merged:
		while (didMergeTwoLeafs) {

			val decisionTreeBefore = decisionTreeModel.toDebugString

			walkThroughNodes(decisionTreeModel.topNode)

			val decisionTreeAfter = decisionTreeModel.toDebugString

			if (decisionTreeBefore == decisionTreeAfter)
				didMergeTwoLeafs = false
		}

		decisionTreeModel
	}

	/** Recursively walks through nodes to find and merge the ones which can be.
	  *
	  * @param node the current node
	  */
	private def walkThroughNodes(node: Node): Unit = {

		if (nodeCanBecomeLeaf(node))
			switchSplitNodeToLeaf(node)

		else
			List(node.leftNode, node.rightNode).flatMap(
				// The flat map is there to both apply the walk on both child
				// nodes, but as well to avoid the walk on leafs:
				x => x
			).foreach(
				childNode =>
					walkThroughNodes(childNode)
			)
	}

	/** Returns if a node with two leaf predicting the same label can be merged as a leaf.
	  *
	  * A node can become a leaf if its children are leafs which predict the
	  * same label.
	  *
	  * @param node the node we're considering to merge as a leaf
	  */
	private def nodeCanBecomeLeaf(node: Node): Boolean = {

		var canBecomeLeaf = false

		if (!node.isLeaf)

			if (
				!node.leftNode.isEmpty && node.leftNode.get.isLeaf &&
				!node.rightNode.isEmpty && node.rightNode.get.isLeaf
			) {

				val leftNode = node.leftNode.get
				val rightNode = node.rightNode.get

				if (leftNode.predict.predict == rightNode.predict.predict)
					canBecomeLeaf = true
			}

		canBecomeLeaf
	}

	/** Transform a node to a leaf node.
	  *
	  * @param node the node to merge as a leaf
	  */
	private def switchSplitNodeToLeaf(node: Node): Unit = {
		node.predict = new Predict(node.leftNode.get.predict.predict, 1d)
		node.isLeaf = true
		node.leftNode = None
		node.rightNode = None
	}
}
