package probe

/**Test for practical associativity with floating point numbers.*/
object FloatAssocApp extends App {

  /**This is mathematically a commutative, and associative operation.
    * But in practice, because of insufficient precision, it does matter, in which order the result is computed.*/
  def f(u: Double, v: Double): Double =
    (u + v) / (1.0 + u * v)

  /**Computes the error (difference of reducing `lst` by function `f` from left or from right).*/
  def err(lst: List[Double]): Double =
    lst.reduceLeft(f) - lst.reduceRight(f)

  def testAssoc: Double = {
    val r = new scala.util.Random
    val lst = List.fill(400)(r.nextDouble * 0.002)
    err(lst)
  }

  for (_ <- 1 to 10) {
    println(testAssoc)
  }

}
