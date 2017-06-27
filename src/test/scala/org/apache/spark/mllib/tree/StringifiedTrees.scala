package org.apache.spark.mllib.tree

object StringifiedTrees {

	final val INITIAL_TREE = """DecisionTreeModel classifier of depth 5 with 63 nodes
  If (feature 0 in {5.0,4.0,0.0})
   If (feature 5 <= 7.0)
    If (feature 1 in {1.0,2.0,4.0,5.0,0.0})
     If (feature 5 <= 0.0)
      If (feature 4 <= 71.0)
       Predict: 0.0
      Else (feature 4 > 71.0)
       Predict: 0.0
     Else (feature 5 > 0.0)
      If (feature 4 <= 23.0)
       Predict: 0.0
      Else (feature 4 > 23.0)
       Predict: 0.0
    Else (feature 1 not in {1.0,2.0,4.0,5.0,0.0})
     If (feature 4 <= 23.0)
      If (feature 2 <= 1432.1433782577515)
       Predict: 1.0
      Else (feature 2 > 1432.1433782577515)
       Predict: 0.0
     Else (feature 4 > 23.0)
      If (feature 4 <= 71.0)
       Predict: 2.0
      Else (feature 4 > 71.0)
       Predict: 0.0
   Else (feature 5 > 7.0)
    If (feature 5 <= 8.0)
     If (feature 1 in {3.0,6.0,1.0,5.0})
      If (feature 2 <= 6272.280740737915)
       Predict: 0.0
      Else (feature 2 > 6272.280740737915)
       Predict: 2.0
     Else (feature 1 not in {3.0,6.0,1.0,5.0})
      If (feature 3 <= 26.0)
       Predict: 2.0
      Else (feature 3 > 26.0)
       Predict: 0.0
    Else (feature 5 > 8.0)
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
  Else (feature 0 not in {5.0,4.0,0.0})
   If (feature 4 <= 122.0)
    If (feature 2 <= 6272.280740737915)
     If (feature 3 <= 202.0)
      If (feature 3 <= 26.0)
       Predict: 0.0
      Else (feature 3 > 26.0)
       Predict: 0.0
     Else (feature 3 > 202.0)
      If (feature 1 in {1.0,0.0,6.0,3.0})
       Predict: 0.0
      Else (feature 1 not in {1.0,0.0,6.0,3.0})
       Predict: 0.0
    Else (feature 2 > 6272.280740737915)
     If (feature 1 in {0.0,4.0,1.0})
      If (feature 5 <= 1.0)
       Predict: 0.0
      Else (feature 5 > 1.0)
       Predict: 0.0
     Else (feature 1 not in {0.0,4.0,1.0})
      If (feature 3 <= 224.0)
       Predict: 0.0
      Else (feature 3 > 224.0)
       Predict: 2.0
   Else (feature 4 > 122.0)
    If (feature 5 <= 1.0)
     If (feature 2 <= 5713.501858711243)
      If (feature 2 <= 5115.069913864136)
       Predict: 2.0
      Else (feature 2 > 5115.069913864136)
       Predict: 0.0
     Else (feature 2 > 5713.501858711243)
      If (feature 3 <= 50.0)
       Predict: 0.0
      Else (feature 3 > 50.0)
       Predict: 2.0
    Else (feature 5 > 1.0)
     If (feature 4 <= 225.0)
      If (feature 1 in {1.0})
       Predict: 2.0
      Else (feature 1 not in {1.0})
       Predict: 0.0
     Else (feature 4 > 225.0)
      If (feature 2 <= 5713.501858711243)
       Predict: 0.0
      Else (feature 2 > 5713.501858711243)
       Predict: 0.0"""

	final val MERGED_LEAFS_TREE = """DecisionTreeModel classifier of depth 5 with 45 nodes
  If (feature 0 in {5.0,4.0,0.0})
   If (feature 5 <= 7.0)
    If (feature 1 in {1.0,2.0,4.0,5.0,0.0})
     Predict: 0.0
    Else (feature 1 not in {1.0,2.0,4.0,5.0,0.0})
     If (feature 4 <= 23.0)
      If (feature 2 <= 1432.1433782577515)
       Predict: 1.0
      Else (feature 2 > 1432.1433782577515)
       Predict: 0.0
     Else (feature 4 > 23.0)
      If (feature 4 <= 71.0)
       Predict: 2.0
      Else (feature 4 > 71.0)
       Predict: 0.0
   Else (feature 5 > 7.0)
    If (feature 5 <= 8.0)
     If (feature 1 in {3.0,6.0,1.0,5.0})
      If (feature 2 <= 6272.280740737915)
       Predict: 0.0
      Else (feature 2 > 6272.280740737915)
       Predict: 2.0
     Else (feature 1 not in {3.0,6.0,1.0,5.0})
      If (feature 3 <= 26.0)
       Predict: 2.0
      Else (feature 3 > 26.0)
       Predict: 0.0
    Else (feature 5 > 8.0)
     If (feature 2 <= 4473.989772796631)
      Predict: 0.0
     Else (feature 2 > 4473.989772796631)
      If (feature 3 <= 126.0)
       Predict: 0.0
      Else (feature 3 > 126.0)
       Predict: 2.0
  Else (feature 0 not in {5.0,4.0,0.0})
   If (feature 4 <= 122.0)
    If (feature 2 <= 6272.280740737915)
     Predict: 0.0
    Else (feature 2 > 6272.280740737915)
     If (feature 1 in {0.0,4.0,1.0})
      Predict: 0.0
     Else (feature 1 not in {0.0,4.0,1.0})
      If (feature 3 <= 224.0)
       Predict: 0.0
      Else (feature 3 > 224.0)
       Predict: 2.0
   Else (feature 4 > 122.0)
    If (feature 5 <= 1.0)
     If (feature 2 <= 5713.501858711243)
      If (feature 2 <= 5115.069913864136)
       Predict: 2.0
      Else (feature 2 > 5115.069913864136)
       Predict: 0.0
     Else (feature 2 > 5713.501858711243)
      If (feature 3 <= 50.0)
       Predict: 0.0
      Else (feature 3 > 50.0)
       Predict: 2.0
    Else (feature 5 > 1.0)
     If (feature 4 <= 225.0)
      If (feature 1 in {1.0})
       Predict: 2.0
      Else (feature 1 not in {1.0})
       Predict: 0.0
     Else (feature 4 > 225.0)
      Predict: 0.0"""
}
