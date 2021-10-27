package samples

/*
ScalaTest also supports the behavior-driven development style, in which you
combine tests with text that specifies the behavior being tested. Here's
an example whose text output when run looks like:

scanLeft
- must compute the fold of an empty Array
- must compute the fold of all empty Array prefixes
 */

import org.scalatest.flatspec.AnyFlatSpec
import org.scalatest.matchers.must.Matchers

class ScanSpec extends AnyFlatSpec with Matchers {

  def scanLeft[A](
      inp: Array[A],
      a0: A,
      f: (A, A) => A,
      out: Array[A]
  ): Unit = {
    out(0) = a0
    for (i <- 0 until inp.length) {
      out(i + 1) = f(out(i), inp(i))
    }
  }

  "scanLeft" must "compute the fold of an empty Array" in {
    val result = new Array[Int](1)
    scanLeft(new Array[Int](0), 100, (x: Int, y: Int) => x + y, result)
    result mustBe Array(100)
  }

  it must "compute the fold of all empty Array prefixes" in {
    val inp = Array(1, 3, 8)
    val result = new Array[Int](inp.length + 1)
    scanLeft(inp, 100, (x: Int, y: Int) => x + y, result)
    result mustBe Array(100, 101, 104, 112)
  }

  //The input tree hierarchy:
  sealed abstract class Tree[A]
  case class Leaf[A](a: A) extends Tree[A]
  case class Node[A](l: Tree[A], r: Tree[A]) extends Tree[A]

  //The output tree hierarchy with result value in each node:
  sealed abstract class TreeRes[A] { val res: A }
  case class LeafRes[A](override val res: A) extends TreeRes[A]
  case class NodeRes[A](l: TreeRes[A], override val res: A, r: TreeRes[A]) extends TreeRes[A]

  def reduceRes[A](t: Tree[A], f: (A, A) => A): TreeRes[A] = {
    t match {
      case Leaf(a) => LeafRes(a)
      case Node(left, right) =>
        val leftRes = reduceRes(left, f)
        val rightRes = reduceRes(right, f)
        NodeRes(leftRes, f(leftRes.res, rightRes.res), rightRes)
    }
  }

  "reduceRes" must "transform a Leaf to a LeafRes with the same value" in {
    reduceRes[Int](Leaf(99), _ + _) mustBe LeafRes(99)
  }

  it must "transform a 2-Leaf tree to a TreeRes with the sum" in {
    val tree = Node(Leaf(10), Leaf(20))
    reduceRes[Int](tree, _ + _) mustBe NodeRes(LeafRes(10), 30, LeafRes(20))
  }

  def prepend[A](x: A, t: Tree[A]): Tree[A] = Node(Leaf(x), t)

}
