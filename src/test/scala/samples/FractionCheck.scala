package samples

import org.scalacheck.Gen
//import org.scalatest.prop.GeneratorDrivenPropertyChecks
import org.scalatestplus.scalacheck.ScalaCheckDrivenPropertyChecks
import org.scalatest.matchers.must.{Matchers}
import org.scalatest.funspec.AnyFunSpec

/**Example of Property Based Testing using ScalaCheck generators, but ScalaTest matchers.
  * Modified example from
  * <a href="http://www.scalatest.org/user_guide/generator_driven_property_checks">ScalaTest Generator-driven property checks</a>*/
class FractionCheck extends AnyFunSpec with Matchers with /*GeneratorDrivenPropertyChecks*/ ScalaCheckDrivenPropertyChecks {

  describe("constructor") {
    it("should not allow Int.MinValue for n or d") {
      assertThrows[IllegalArgumentException] {
        new Fraction(Int.MinValue, 1)
      }
      assertThrows[IllegalArgumentException] {
        new Fraction(1, Int.MinValue)
      }
    }
    it("should not allow 0 for d") {
      assertThrows[IllegalArgumentException] {
        new Fraction(1, 0)
      }
    }
  }
  describe("denom") {
    it("should always be positive") {
      forAll("n", "d", minSuccessful(100)) {
        (n: Int, d: Int) =>
          whenever(d != 0 && d != Integer.MIN_VALUE && n != Integer.MIN_VALUE) {
            val f = new Fraction(n, d)
            f.denom must be > 0
          }
      }
    }
  }
  describe("numer") {
    it("should be the only to express negativity") {
      forAll("n", "d", minSuccessful(100)) {
        (n: Int, d: Int) =>
          whenever(d != 0 && d != Integer.MIN_VALUE && n != Integer.MIN_VALUE) {
            val f = new Fraction(n, d)
            if (n == 0) {
              f.numer mustBe 0
            } else if (n < 0 && d < 0 || n > 0 && d > 0) {
              f.numer must be > 0
            } else {
              f.numer must be < 0
            }
          }
      }
    }
  }
  describe("Gen.choose") {
    it("should generate values in inclusive range") {
      forAll(Gen.choose(1, 10), minSuccessful(100)) {
        (x: Int) =>
          //Although choose() generates only numbers in range 1 to 10, shrinking a failed argument involves negative numbers.
          //That is why we need a redundant whenever clause! 2019-07-15
          whenever(1 <= x && x <= 10) {
            x must (be >= 1 and be <= 10)
          }
      }
    }
  }

}
