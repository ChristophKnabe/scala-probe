package platform

/*
Here's an example of a FunSuite with must.Matchers mixed in:
 */
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers

class SeqSuite extends AnyFunSuite with Matchers {

  test("An empty sequence must be empty") {
    Seq() mustBe empty
    Seq.empty mustBe empty
  }
  test("A non-empty sequence must not be empty") {
    Seq(1, 2, 3) must not be empty
    Seq("fee", "fie", "foe", "fum") must not be empty
  }
  test("A sequence's length must equal the number of elements it contains") {
    Seq() must have length (0)
    Seq(1, 2) must have length (2)
    Seq("fee", "fie", "foe", "fum") must have length (4)
  }
  test("Augmenting a sequence") {
    Seq(1, 2) :+ 3 mustBe Seq(1, 2, 3)
    Seq(1, 2).+:(3) mustBe Seq(3, 1, 2)
    3 +: Seq(1, 2) mustBe Seq(3, 1, 2)
    Seq(1, 2) ++ Seq(3, 4) mustBe Seq(1, 2, 3, 4)
  }
  test("Updating a sequence") {
    val seq3 = Seq("Alpha", "Beta", "Gamma")
    seq3.updated(0, "XXX") mustBe Seq("XXX", "Beta", "Gamma")
    seq3.updated(1, "XXX") mustBe Seq("Alpha", "XXX", "Gamma")
    seq3.updated(2, "XXX") mustBe Seq("Alpha", "Beta", "XXX")
    assertThrows[IndexOutOfBoundsException] { seq3.updated(3, "XXX") }
  }
  test("remove an element from a sequence by content") {
    val seq = Seq("Alpha", "Beta", "Gamma")
    seq.filter(_ != "XXX") mustBe Seq("Alpha", "Beta", "Gamma")
    seq.filter(_ != "Alpha") mustBe Seq("Beta", "Gamma")
    seq.filter(_ != "Beta") mustBe Seq("Alpha", "Gamma")
    seq.filter(_ != "Gamma") mustBe Seq("Alpha", "Beta")
    seq.withFilter(_ != "ABX")
  }

}
