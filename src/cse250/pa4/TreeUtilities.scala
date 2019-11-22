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
    var heapArray: Array[A] = Array()
    var queue: mutable.Queue[Tree[A]] = mutable.Queue()
    if(root != Empty){
      queue.enqueue(root)
      while(queue.nonEmpty){
        val node = queue.dequeue()
        val left = node.left.get
        val right = node.right.get
        heapArray :+= node.value.get
        if(left != Empty){
          queue.enqueue(left)
        }
        if(right != Empty){
          queue.enqueue(right)
        }
      }
    }
    heapArray
  }

  def isValidBinaryHeap[A](root: Tree[A])(implicit comp: Ordering[A]): Boolean = {
    //Helper Functions
    def size(root: Tree[A]): Int = {//Finds size of tree
      if(root.value.isDefined) 1 + size(root.left.get) + size(root.right.get)
      else 0
    }
    println("SIZE: " + size(root))
    def checkComplete(root: Tree[A], indX: Int, size: Int): Boolean = {//Check the tree for proper creation
      if (root.value.isEmpty) true else if (indX >= size) false else checkComplete(root.left.get, 2 * indX + 1, size) && checkComplete(root.right.get, 2 * indX + 2, size)
    }
    println("COMPLETE TREE: " + checkComplete(root, 0, size(root)))
    def checkHeap(root: Tree[A]): Boolean = {//Checks the proper value
      if(root.left.get.value.isEmpty && root.right.get.value.isEmpty) true
      else if(root.right.get.value.isEmpty) comp.gteq(root.value.get, root.left.get.value.get)
      else if (comp.gteq(root.value.get, root.left.get.value.get) && comp.gteq(root.value.get, root.right.get.value.get)) checkHeap(root.left.get) && checkHeap(root.right.get)
      else false
    }
    println("CORRECT HEAP: " + checkHeap(root))
    if(checkComplete(root, 0, size(root)) && checkHeap(root)) true else false
  }

  def applyTree[A](root: Tree[A], index: Int): Option[A] = {
    var path: Array[Double] = Array()
    var indX: Double = index
    while(indX >= 0){//Should be O(log(n))
      if(indX != 0){
        path :+= indX
      }
      indX = (indX -2) / 2
      if(indX > indX.toInt){
        indX += 0.5
      }
    }
    path = path.reverse

    var start: Tree[A] = root
    if(index == 0){
      start.value
    }
    else if(path.nonEmpty){
      for(node <- path){
        if(node % 2 == 1){//Odd
          //Goto left
          if(start.left.isDefined){
            start = start.left.get
          }
          else{
            return None
          }
        }
        else{
          if(start.right.isDefined){
            start = start.right.get
          }
          else{
            return None
          }
        }
      }
      start.value
    }
    else{
      None
    }
  }

  def updateHeap[A](root: Tree[A], index: Int, elem: A)(implicit comp: Ordering[A]): Tree[A] = {
    root
  }
}
