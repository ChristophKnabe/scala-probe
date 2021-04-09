package samples

import org.scalacheck.Properties
import org.scalacheck.Prop.forAll

object ListCheck extends Properties("List") {

  property("reverse") = forAll { l: List[Int] =>
    l.reverse.reverse == l
  }

  property("head :: tail") = forAll { l: List[Int] =>
    println(l);
    l.isEmpty || l.head :: l.tail == l
  }

}
