package platform

import java.net.URLDecoder
import org.junit.Test
import org.scalatest.matchers.must.Matchers

/** Test driver to gain knowledge about the URLDecoder.
  * @author Christoph Knabe */
class URLDecoderTest extends Matchers {

  /** Symbolic name for the UTF-8 Charset. */
  val utf8Charset = scala.io.Codec.UTF8.charSet

  @Test def testDecodeSucceeds(): Unit = {
    assertResult("abc def") {
      URLDecoder.decode("abc%20def", "ISO-8859-1")
    }
    assertResult("abc def") {
      URLDecoder.decode("abc%20def", "UTF-8")
    }
    assertResult("abc def") {
      URLDecoder.decode("abc%20def", utf8Charset)
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
