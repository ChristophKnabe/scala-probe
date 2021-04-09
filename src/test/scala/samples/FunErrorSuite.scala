package samples

import java.util.concurrent.TimeoutException

import org.scalatest.funsuite.AsyncFunSuite

//import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.concurrent.{Await, Future}

/** Tests the error propagation with Futures. */
class FunErrorSuite extends AsyncFunSuite {
  //For asynchronous tests see accepted answer at https://stackoverflow.com/questions/55487833/scalatest-asynchronous-test-suites-vs-eventually-and-whenready-org-scalatest-co
  //We here implement the first approach with an asynchronous test suite. This is thread-safe, but you can have only one assertion per test case.
  //The ExecutionContext is taken from the async test suite.
  //Each test case must return a Future[Assertion], but it seems to work, too, if it returns simply an Assertion.

  test("A thrown exception can be asserted synchronously by assertThrows") {
    assertThrows[IllegalArgumentException] {
      throw new IllegalArgumentException
    }
  }

  test("A thrown exception can be asserted asynchronously by recoverToSucceededIf") {
    recoverToSucceededIf[IllegalArgumentException] {
      Future {
        throw new IllegalArgumentException
      }
    }
  }

  test("A value computed in a Future is accesible by map") {
    Future[Int] {
      42
    }.map {
      i => assertResult(42)(i)
    }
  }

  test("An exception thrown in a Future will not be noticed unless the result is used") {
    Future[Int] { throw new Exception("Could not compute the Int") }
    assert(true)
  }

  test("An exception thrown in a Future hinders the Future to complete") {
    /*Purpose is proving the sentence "In the case that the future fails, the caller is forwarded the exception that the future is failed with."
     * from the document https://docs.scala-lang.org/overviews/core/futures.html in the section "Blocking outside the Future".
     * Unfortunately in the calling thread a TimeoutException is thrown instead of the expected IllegalArgumentException.
     */
    assertThrows[TimeoutException](Await.result(Future { throw new IllegalArgumentException }, 1.second))
  }

  test("An failed Future terminates the blocking Await.result with its exception") {
    /*Purpose is to prove the behavior described in https://medium.com/@sderosiaux/are-scala-futures-the-past-69bd62b9c001
     * how to pass an exception from the Future to the main thread. */
    val crash: Future[Duration] = Future.failed(new IllegalArgumentException("boom"))
    val exc = intercept[IllegalArgumentException](Await.result(crash, Duration.Inf))
    assertResult("boom") { exc.getMessage }
  }

  test("A Future terminated by a thrown exception will unfortunately not complete") {
    val crash: Future[Duration] = Future {
      throw new IllegalArgumentException("boom")
    }
    assertThrows[TimeoutException](Await.result(crash, 1.second))
  }

  test("An exception thrown in a Future propagates to the main thread by mapping on it") {
    recoverToSucceededIf[ArithmeticException] {
      Future[Int] {
        throw new ArithmeticException("Could not compute the Int")
      }
    }
  }

}
