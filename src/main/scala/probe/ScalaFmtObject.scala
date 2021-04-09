package probe

/** This class serves as an object, which will be formatted by ScalaFmt,
  * as configured in file `.scalafmt.conf`. */
class ScalaFmtObject {

  //Alignment tests with align=none
  System.currentTimeMillis match { // false for case arrows
    case 2 => 22 // Do not align according to following line
    case 222222222222L => 222 // don't align me!
  }

  //align.openParenDefnSite = false
  def function(
      a: Int,
      b: Int,
      c: Int
  ) = a + b + c

  //align.openParenCallSite = false
  function(
    127,
    989,
    8767
  )

  //assumeStandardLibraryStripMargin = true
  //align.stripMargin = true
  val key =
    """Hello
      |world""".stripMargin

  //continuationIndent.extendSite = 0
  //continuationIndent.withSiteRelativeToExtends = 2
  trait A
  trait B
  trait C

  class Foo extends Serializable with A with B with C {
    def foo: Boolean = true
  }

  def f(list: List[Int]) =
    list
      .map(x => x * 2)
      .map(y => y * 3)
      .fold(10)(_ + _)

}
