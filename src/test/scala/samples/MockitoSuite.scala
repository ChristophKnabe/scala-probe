/*
 * Copyright (c) 2020 Aaron Coburn and individual contributors
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package samples

import org.scalatest.funsuite.AnyFunSuite
import org.scalatest.matchers.must.Matchers
import org.mockito.MockitoSugar

object MockitoSuite {

  trait MyTrait {
    def myMethod: String
  }

}

/** A test suite showing how to use Mockito Scala to mock a trait.
  * See https://github.com/mockito/mockito-scala */
class MockitoSuite extends AnyFunSuite with Matchers with MockitoSugar {

  import MockitoSuite._

  test("MyTrait.myMethod returns hello") {
    //1. Arrange:
    val myMock = mock[MyTrait] //Mock the class MyClass
    doReturn("hello").when(myMock).myMethod //Configure myMock to return "hello" on call of myMethod
    //2. Act:
    val result = myMock.myMethod
    //3. Assert:
    result mustBe "hello"
  }

}
