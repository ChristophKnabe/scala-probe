package samples

/*
ScalaTest also supports the behavior-driven development style, in which you
combine tests with text that specifies the behavior being tested. Here's
an example whose text output when run looks like:

An IterativeListService
- should compute the minimum value of a short List
- should compute the minimum value of a long List
 */

import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class ListSpec extends AnyFunSpec with Matchers {

  describe("An IterativeListService") {
    testMinimum(new IterativeListService)
  }

  describe("A RecursiveListService") {
    testMinimum(new RecursiveListService)
  }

  private def testMinimum(testee: ListService): Unit = {
    it("should compute the minimum value of a short List") {
      testee.min(List[Int]()) must be(Int.MaxValue)
      testee.min(List(1)) must be(1)
      testee.min(List(4, 9)) must be(4)
      testee.min(List(9, 4)) must be(4)
      testee.min(List(9, 4, 12)) must be(4)
    }
    it("should compute the minimum value of a long List") {
      val list = List.range(100000, 0, -1)
      testee.min(list) must be(1)
    }
  }

}
