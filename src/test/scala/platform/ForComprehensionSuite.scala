package platform

/*
Playing around with for comprehensions
@author Christoph Knabe
@since 2021-10-29
 */
import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers

class ForComprehensionSuite extends AnyFunSuite with Matchers {

  test("A for comprehension creates combinations of all its generated items") {
    val seq1 = 1 to 3
    val seq2 = List('A', 'B', 'C', 'D')
    val result = for {
      i <- seq1
      c <- seq2
    } yield i.toString + c
    result mustBe Seq("1A", "1B", "1C", "1D", "2A", "2B", "2C", "2D", "3A", "3B", "3C", "3D")
  }
  test("A for comprehension can flatten Options in a Seq") {
    val numbers = 1 to 10
    val evenNumbers: Seq[Option[Int]] = numbers map (i => if (i % 2 == 0) Some(i) else None)
    val result = for {
      numberOption <- evenNumbers
      evenNumber <- numberOption
    } yield evenNumber
    result mustBe Seq(2, 4, 6, 8, 10)
  }
  test("A for comprehension can flatten Seqs in a Seq") {
    val numbers = 1 to 10
    val groupedNumbers: Iterator[Range] = numbers.grouped(3)
    println(s"groupedNumbers=$groupedNumbers")
    val result = for {
      group <- groupedNumbers
      number <- group
    } yield number
    result.toSeq mustBe Seq(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
  }
  test("A for comprehension cannot flatten a Seq in an Option unless converted to a Seq") {
    val numbers = 1 to 10
    val numbersOption: Option[Seq[Int]] = Some(numbers)
    val result = for {
      numbers <- numbersOption.toSeq
      number <- numbers
    } yield number
    result mustBe Seq(1, 2, 3, 4, 5, 6, 7, 8, 9, 10)
  }

}
