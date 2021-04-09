package platform

import java.io.{File, FileInputStream, InputStream}

import org.junit.Test
import org.scalatest.matchers.must.Matchers

/** Test driver to gain knowledge about class java.io.InputStream.
  *
  * @author Christoph Knabe
  * @since 2021-03-24 */
class InputStreamTest extends Matchers {

  val classname = getClass.getSimpleName

  /** Efficiently consumes the `inputStream`, closes it and returns the number of bytes read. Time consumption is linear,
    * on a 2 GHz processor blocks one thread and needs about 2 seconds for each Gigabyte. */
  def countBytes(inputStream: InputStream): Long = {
    var sum: Long = 0
    val bufferSize = 2048 //like private java.io.InputStream.MAX_SKIP_BUFFER_SIZE
    val buffer = new Array[Byte](bufferSize)
    try {
      while (true) {
        val bytesRead = inputStream.read(buffer, 0, bufferSize)
        if (bytesRead < 0) return sum
        sum += bytesRead
      }
      sum
    } finally {
      inputStream.close()
    }
  }

  @Test def countBytesInSmallFile(): Unit = {
    _testCountBytesInFile("src/test/resources/wilhelm.png")
  }

  @Test def countBytesInBigFile(): Unit = {
    _testCountBytesInFile("/home/knabe/Downloads/ubuntu-20.04.2.0-desktop-amd64.iso")
  }

  @Test def countBytesInSmallGeneratedStream(): Unit = {
    _testCountBytesInGeneratedInputStream(1000)
  }

  @Test def countBytesInBigGeneratedStream(): Unit = {
    _testCountBytesInGeneratedInputStream(2000 * 1000 * 10000L)
  }

  private def _testCountBytesInFile(filename: String): Unit = {
    val inFile = new File(filename).getCanonicalFile
    val length = inFile.length()
    val in = new FileInputStream(inFile)
    assertResult(length) {
      countBytes(in)
    }
    println(s"$classname: File $inFile has $length bytes.")
  }

  private def _testCountBytesInGeneratedInputStream(length: Long): Unit = {
    val in = new GeneratedInputStream(length)
    val className = in.getClass.getSimpleName
    println(s"Counting bytes in $className of length $length")
    assertResult(length) {
      countBytes(in)
    }
  }

  /** An InputStream generating `length` bytes with varying content. */
  private class GeneratedInputStream(val length: Long) extends InputStream {
    val EOF = -1
    private var count: Long = 0

    override def read(): Int = {
      if (count < length) {
        val result = (count % 256).toInt
        count += 1
        result
      } else {
        EOF
      }
    }

  }

}
