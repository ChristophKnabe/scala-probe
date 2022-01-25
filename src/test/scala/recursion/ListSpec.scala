package recursion

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

  private val iterativeListService = new IterativeListService
  private val tailRecursiveListService = new TailRecursiveListService
  private val middleRecursiveListService = new MiddleRecursiveListService

  describe("An " + iterativeListService.name) {
    testMinimum(iterativeListService)
  }

  describe("A " + tailRecursiveListService.name) {
    testMinimum(tailRecursiveListService)
  }

  describe("A " + middleRecursiveListService.name) {
    testMinimum(middleRecursiveListService)
  }

  private def testMinimum(testee: ListService): Unit = {
    it("should compute the minimum value of a short List") {
      testee.min(List[Int]()) mustBe Int.MaxValue
      testee.min(List(1)) mustBe 1
      testee.min(List(4, 9)) mustBe 4
      testee.min(List(9, 4)) mustBe 4
      testee.min(List(9, 4, 12)) mustBe 4
    }
    it("should compute the minimum value of a long List") {
      val list = List.range(100000, 0, -1)
      testee.min(list) must be(1)
    }
  }

}
