package probe

/** Demonstrates the definition and usage of a type class.
  * Follows article https://scalac.io/blog/typeclasses-in-scala/ or https://medium.com/@kolemannix/an-introduction-to-typeclasses-in-scala-26d4dc5fdf58
  * @since 2021-03-31 */
object TypeclassDemo {

  /** A type class abstracting the possibility to show values of any type. */
  trait Showable[A] {
    def show(a: A): String
  }

  /** Implementations for a Typeclass [[Showable]].*/
  object Showable {

    def make[A](showFn: A => String): Showable[A] =
      new Showable[A] {
        override def show(a: A): String = showFn(a)
      }

    //def show[A](a: A)(implicit showable: Shower[A]) = showable.show(a)
    def show[A: Showable](a: A) = Showable[A].show(a)
    def apply[A](implicit shower: Showable[A]): Showable[A] = shower

    implicit val intCanShow: Showable[Int] = Showable.make[Int] {
      value => s"Int $value"
    }

    implicit val doubleCanShow: Showable[Double] = Showable.make[Double] {
      value => s"Double $value"
    }

  }

  def main(args: Array[String]): Unit = {
    println("intCanShow shows 20 as " + Showable.intCanShow.show(20))
    println("Showable shows 20 as " + Showable.show(20))
    println("Showable shows 10.0 as " + Showable.show(10.0))
  }

}
