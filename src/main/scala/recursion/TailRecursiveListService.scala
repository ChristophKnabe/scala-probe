package recursion

import scala.annotation.tailrec

/**
  * Created by knabe on 28.03.17.
  */
class TailRecursiveListService extends ListService {

  /** Computes the minimum value from the list efficiently by tail recursion. */
  override def min(list: List[Int]): Int = _min(Int.MaxValue, list)

  @tailrec
  private def _min(minAccu: Int, list: List[Int]): Int = {
    list match {
      case Nil => minAccu
      case head :: tail =>
        val newMin = Math.min(head, minAccu)
        _min(newMin, tail) //tail recursion as last computation in the function
    }
  }

}
