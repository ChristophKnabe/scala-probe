package recursion

/**
  * Created by knabe 2022-01-25.
  */
class MiddleRecursiveListService extends ListService {

  /** Computes the minimum value from the list inefficiently by traditional, middle recursion.
    * May fail on long Lists by StackOverflowError */
  override def min(list: List[Int]): Int = {
    list match {
      case Nil => Int.MaxValue
      case head :: tail =>
        //middle recursion:
        val tailMin = min(tail);
        //Computing after the recursion prohibits tail-call-optimization!
        Math.min(head, tailMin)
    }
  }

}
