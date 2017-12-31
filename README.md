# Mllib Decision Tree Reducer [![Build Status](https://travis-ci.org/XavierGuihot/mllib_decision_tree_reducer.svg?branch=master)](https://travis-ci.org/XavierGuihot/mllib_decision_tree_reducer) [![Coverage Status](https://coveralls.io/repos/github/xavierguihot/mllib_decision_tree_reducer/badge.svg?branch=master)](https://coveralls.io/github/xavierguihot/mllib_decision_tree_reducer?branch=master)


## Overview


Version: 1.0.0

API Scaladoc: [Reducer](https://xavierguihot.github.io/mllib_decision_tree_reducer/#org.apache.spark.mllib.tree.Reducer$)

Small facility which reduces naive decision tree models as produced by mllib.

Mllib produces trees for which some nodes have two leafs predicting the same
label, for instance:

	If (feature 5 > 8.0)
	 If (feature 2 <= 4473.989772796631)
	  If (feature 0 in {4.0})
	   Predict: 0.0
	  Else (feature 0 not in {4.0})
	   Predict: 0.0
	 Else (feature 2 > 4473.989772796631)
	  If (feature 3 <= 126.0)
	   Predict: 0.0
	  Else (feature 3 > 126.0)
	   Predict: 2.0

which can be optimized (simplified) for projects applying this decision tree
model at large scale and needing high performance (less nodes means less
conditions and thus less cpu usage):

	If (feature 5 > 8.0)
	 If (feature 2 <= 4473.989772796631)
	  Predict: 0.0
	 Else (feature 2 > 4473.989772796631)
	  If (feature 3 <= 126.0)
	   Predict: 0.0
	  Else (feature 3 > 126.0)
	   Predict: 2.0

This is particularly helpfull for decision trees with fewer possible labels such
as a true/false classification tree.


## Using mllib_decision_tree_reducer:


For additional details: API Scaladoc: [Reducer](https://xavierguihot.github.io/mllib_decision_tree_reducer/#org.apache.spark.mllib.tree.Reducer$)

	import org.apache.spark.mllib.tree.Reducer
	import org.apache.spark.mllib.tree.model.DecisionTreeModel // spark mllib

	val reducedDecisionTreeModel: DecisionTreeModel = Reducer.mergeLeafs(naiveDecisionTreeModel)


## Including mllib_decision_tree_reducer to your dependencies:


With sbt, just add this one line to your build.sbt:

	libraryDependencies += "mllib_decision_tree_reducer" % "mllib_decision_tree_reducer" % "1.0.0" from "https://github.com/xavierguihot/mllib_decision_tree_reducer/releases/download/v1.0.0/mllib_decision_tree_reducer-1.0.0.jar"


## Building the project:


With sbt:

	sbt assembly
