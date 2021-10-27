package samples

/*
ScalaTest also supports the behavior-driven development style, in which you
combine tests with text that specifies the behavior being tested. Here's
an example whose text output when run looks like:

A Map
- must only contain keys and values that were added to it
- must report its size as the number of key/value pairs it contains
 */
import org.scalatest.funspec.AnyFunSpec
import org.scalatest.matchers.must.Matchers

class MapSpec extends AnyFunSpec with Matchers {

  describe("A Map") {

    it("must only contain keys and values that were added to it") {
      Map("ho" -> 12) must (not contain key("hi") and not contain value(13))
      Map("hi" -> 13) must (contain key ("hi") and contain value (13))
    }

    it("must report its size as the number of key/value pairs it contains") {
      Map[String, Int]() must have size 0
      Map("ho" -> 12) must have size 1
      Map("hi" -> 13, "ho" -> 12) must have size 2
    }
  }
}
