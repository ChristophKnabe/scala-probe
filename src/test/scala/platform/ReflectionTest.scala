package platform

import java.lang.invoke.MethodHandles
import java.net.URLDecoder

import org.junit.Test
import org.scalatest.matchers.must.Matchers

/** Test driver to gain knowledge about class MethodHandles.
  *
  * @author Christoph Knabe */
class ReflectionTest extends Matchers {

  /** Symbolic name for the UTF-8 Charset. */
  val utf8Charset = scala.io.Codec.UTF8.charSet

  @Test def lookupClass(): Unit = {
    assertResult(classOf[ReflectionTest]) {
      MethodHandles.lookup.lookupClass
    }
    assertResult("platform.ReflectionTest") {
      MethodHandles.lookup.lookupClass.getName
    }
  }

  @Test def testDecodeThrows(): Unit = {
    assertThrows[IllegalArgumentException] {
      URLDecoder.decode("abc%1", utf8Charset) // missing hex digit at end of %escape sequence
    }
    assertThrows[IllegalArgumentException] {
      URLDecoder.decode("%0G", utf8Charset) // %escape sequence with illegal hex character
    }
  }

}
