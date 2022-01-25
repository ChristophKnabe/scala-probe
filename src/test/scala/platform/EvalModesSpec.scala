package platform

/*
Evaluation modes in Scala:
Strict vs. Lazy,
Once vs. Repeatedly.
 */
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class EvalModesSpec extends AnyFunSpec with Matchers {

  describe("Strict evaluation (exactly once) holds for") {
    it("val: when its definition is elaborated") {
      var count = 0
      val value = count += 1 //definition here
      count mustBe 1
      useStrict(value)
    }
    it("argument: when passed to a called method/function") {
      var count = 0
      useStrict(count += 1) //argument passing here
      count mustBe 1
    }
  }

  describe("Lazy evaluation (at most once) holds for") {
    it("lazy val: not yet at its definition") {
      var count = 0
      lazy val value = count += 1 //definition here
      count mustBe 0
      useStrict(value)
    }
    it("lazy val: when it is used first time after definition") {
      var count = 0
      lazy val value = count += 1 //definition here
      useStrict(value)
      count mustBe 1
    }
  }

  describe("At least zero evaluation holds for") {
    it("by-name arguments: not yet when passed to a called method/function") {
      var count = 0
      useByName(arg = count += 1, howOften = 0) //argument passing here
      count mustBe 0
    }
    it("by-name arguments: so often as used inside the called method/function") {
      var count = 0
      useByName(arg = count += 1, howOften = 5) //argument passing here
      count mustBe 5
    }
    it("def: not yet at its definition") {
      var count = 0
      def method = count += 1 //definition here
      count mustBe 0
      useStrict(method)
    }
    it("def: so often as it is called") {
      var count = 0
      def method() = count += 1 //definition here
      method()
      method()
      count mustBe 2
    }
  }

  private def useStrict(any: Any): Unit = { any.toString }

  private def useByName(arg: => Any, howOften: Int): Unit =
    for (_ <- 1 to howOften) {
      arg.toString
    }

}
