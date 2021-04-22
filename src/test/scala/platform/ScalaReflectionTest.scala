package platform

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers
import platform.ScalaReflectionTest.{CreditLimitExceeded, Reportable}

import scala.reflect.runtime.{universe => ru}

/** Test driver to gain knowledge about the Scala 2.10 Reflection.
  *
  * @author Christoph Knabe */
class ScalaReflectionTest extends AnyFunSuite with Matchers {

  test("get simple class name") {
    assertResult(classOf[ScalaReflectionTest].getSimpleName) {
      ru.typeOf[ScalaReflectionTest].typeSymbol.name.toString
    }
  }
  test("get simple object name") {
    assertResult(ScalaReflectionTest.getClass.getSimpleName.stripSuffix("$")) {
      ru.typeOf[ScalaReflectionTest.type].typeSymbol.name.toString
    }
  }
  test("get static type simple name of reference") {
    val cle: Reportable = CreditLimitExceeded(1000, 23)
    assertResult(classOf[Reportable].getSimpleName) {
      ru.typeOf[cle.type].typeSymbol.name.toString
    }
  }
  test("get dynamic type simple name of reference") {
    val cle: Reportable = CreditLimitExceeded(1000, 23)
    assertResult(classOf[CreditLimitExceeded].getSimpleName) {
      ru.runtimeMirror(getClass.getClassLoader).reflect(cle).symbol.name.toString
    }
  }

}

object ScalaReflectionTest {

  sealed abstract class Reportable(val message: String)

  final case class CreditLimitExceeded(amount: Int, exceed: Int)
  extends Reportable(s"Withdrawal of $amount units exceed the credit limit by $exceed units.")

}
