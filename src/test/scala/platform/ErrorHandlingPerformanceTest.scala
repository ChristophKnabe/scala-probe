package platform

import org.junit.Test
import org.scalatest.matchers.must.Matchers

/** Test driver to gain knowledge about the time consumption of different error handling techniques.
  * It seems that throw/catch exception lasts up to 26 times longer than return/match Either.
  * A significant difference is observable only from 1 million calls upwards.
  * @author Christoph Knabe */
class ErrorHandlingPerformanceTest extends Matchers {

  private val max = 100000000

  @Test def oddsByException(): Unit = {
    assertResult(max / 2) {
      ByException.oddNumbersUntil(max)
    }
  }

  @Test def oddsByEither(): Unit = {
    assertResult(max / 2) {
      ByEither.oddNumbersUntil(max)
    }
  }

  object ByException {

    class OddNumber(number: Int) extends Exception(s"Illegal odd number $number")

    /** @throws OddNumber the `number` is odd.*/
    private def checkEven(number: Int): Unit = {
      if (number % 2 != 0) {
        throw new OddNumber(number)
      }
    }

    def oddNumbersUntil(max: Int): Int = {
      var result = 0
      var i = 0
      while (i < max) {
        try {
          checkEven(i)
        } catch {
          case _: OddNumber => result += 1
        }
        i += 1
      }
      result
    }

  }

  object ByEither {

    sealed abstract class Error(val message: String)
    case class OddNumber(number: Int) extends Error(s"Illegal odd number $number")

    /** @throws OddNumber the `number` is odd.*/
    private def checkEven(number: Int): Either[OddNumber, Unit] = {
      number % 2 match {
        case 0 => Right(())
        case 1 => Left(OddNumber(number))
      }
    }

    def oddNumbersUntil(max: Int): Int = {
      var result = 0
      var i = 0
      while (i < max) {
        checkEven(i) match {
          case Left(_) => result += 1
          case _ =>
        }
        i += 1
      }
      result
    }

  }

}
