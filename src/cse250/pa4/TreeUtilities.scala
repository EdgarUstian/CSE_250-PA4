/**
 * cse250.pa4.TreeUtilities.scala
 *
 * Copyright 2019 Andrew Hughes (ahughes6@buffalo.edu)
 *
 * This work is licensed under the Creative Commons
 * Attribution-NonCommercial-ShareAlike 4.0 International License.
 * To view a copy of this license, visit
 * http://creativecommons.org/licenses/by-nc-sa/4.0/.
 *
 * Submission author
 * UBIT: edgarust
 * Person#: 50230866
 *
 * Collaborators (include UBIT name of each, comma separated):
 * UBIT:
 */
package cse250.pa4

import cse250.objects.{Empty, Node, Tree}

import scala.collection.mutable
import scala.reflect.ClassTag

object TreeUtilities {
  def buildHeapTreeFromHeapArray[A](heapArray: Array[A]): Tree[A] = {
    val arraySize = heapArray.length
    def makeTree(indX: Int): Tree[A] = {
      if(indX < arraySize) Node[A](heapArray(indX), makeTree(2*indX + 1), makeTree(2*indX + 2))
      else Empty
    }
    if(heapArray.nonEmpty) makeTree(0)
    else Empty
  }

  def flattenHeapTreeToHeapArray[A: ClassTag](root: Tree[A]): Array[A] = {
    def makeArray(root: Tree[A]): Array[A] = {
      val heapArray: Array[A] = Array()
      if(root != Empty){
        heapArray :+ root.value
        makeArray(root.left.get)
        makeArray(root.right.get)
      }
      heapArray
    }
    makeArray(root)
  }

  def isValidBinaryHeap[A](root: Tree[A])(implicit comp: Ordering[A]): Boolean = {
    false
  }

  def applyTree[A](root: Tree[A], index: Int): Option[A] = {
    None
  }

  def updateHeap[A](root: Tree[A], index: Int, elem: A)(implicit comp: Ordering[A]): Tree[A] = {
    root
  }
}
