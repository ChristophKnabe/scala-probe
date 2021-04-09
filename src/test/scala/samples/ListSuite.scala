package samples

/*
Here's an example of a FunSuite with must.Matchers mixed in:
 */
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.{Matchers}

class ListSuite extends AnyFunSuite with Matchers {

  test("An empty list must be empty") {
    List() mustBe empty
    Nil mustBe empty
  }

  test("A non-empty list must not be empty") {
    List(1, 2, 3) must not be empty
    List("fee", "fie", "foe", "fum") must not be empty
  }

  test("A list's length must equal the number of elements it contains") {
    List() must have length (0)
    List(1, 2) must have length (2)
    List("fee", "fie", "foe", "fum") must have length (4)
  }
}
