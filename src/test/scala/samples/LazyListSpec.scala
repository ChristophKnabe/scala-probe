package samples

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

/** Tests an internal implementation of the sieve of Eratosthenes using a [[LazyList]]. */
class LazyListSpec extends AnyFunSpec with Matchers {

  /** Returns a [[LazyList]] of all natural numbers starting from n ascending. */
  def from(n: Int): LazyList[Int] = n #:: from(n + 1)

  /** Returns a [[LazyList]] of all prime numbers in positive Int range. */
  def primeSieve(s: LazyList[Int]): LazyList[Int] =
    s.head #:: primeSieve(s.tail filter (_ % s.head != 0))

  describe("from") {
    it("must be only computed for first number") {
      from(10).toString mustBe "LazyList(<not computed>)"
      from(20).toString mustBe "LazyList(<not computed>)"
    }
    it("must be materialized by .toList") {
      from(1).take(5).toList mustBe List(1, 2, 3, 4, 5)
    }
  }

  describe("primeSieve") {
    it("must be partially computed for a finite argument") {
      primeSieve(from(2)) must (contain(2))
      primeSieve(from(2)) must (contain(3))
      primeSieve(from(2)) must (contain(5))
      primeSieve(from(2)) must (contain(997))
    }
    it("must return all prime numbers") {
      primeSieve(from(2)).take(10) mustBe Seq(2, 3, 5, 7, 11, 13, 17, 19, 23, 29)
    }
  }

  //StackOverflowError
  //lazy val sums: Stream[(Int,Int)] = (1,1) #:: sums.zip(sums.tail).map { case (alt:(Int,Int),neu:(Int,Int)) => (neu._1, alt._2 + neu._1) }
  /*val sums = 1 #:: sums.map(from: Int)
  def sums(n: Int): Stream[Int] = 1 #:: sums.zip(sums.tail).map { case (alt:(Int,Int),neu:(Int,Int)) => (neu._1, alt._2 + neu._1) }

  describe("sums") {
    it("must return all pairs (n, sum up to n)") {
      sums.take(5) mustBe Seq((1,1), (2,3), (3,6), (4,10), (5,15))
    }
  }*/

  def fibFrom(a: Int, b: Int): LazyList[Int] = a #:: fibFrom(b, a + b)

  describe("fibFrom") {
    it("must return all Fibonacci numbers") {
      fibFrom(1, 1).take(7) mustBe Seq(1, 1, 2, 3, 5, 8, 13)
    }
  }

  def sumsFrom(accu: Int, start: Int): LazyList[Int] = {
    val sum = accu + start
    sum #:: sumsFrom(sum, start + 1)
  }

  describe("sumsFrom") {
    it("must return all Gauß sums") {
      sumsFrom(0, 1).take(8) mustBe Seq(1, 3, 6, 10, 15, 21, 28, 36)
    }
  }

}
