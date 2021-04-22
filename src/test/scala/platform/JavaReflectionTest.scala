package platform

import java.lang.invoke.MethodHandles

import org.junit.Test
import org.scalatest.matchers.must.Matchers

/** Test driver to gain knowledge about class MethodHandles.
  *
  * @author Christoph Knabe */
class JavaReflectionTest extends Matchers {

  @Test def lookupClass(): Unit = {
    assertResult(classOf[JavaReflectionTest]) {
      MethodHandles.lookup.lookupClass
    }
    assertResult("platform.JavaReflectionTest") {
      MethodHandles.lookup.lookupClass.getName
    }
  }

}
