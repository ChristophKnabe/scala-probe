package samples

import scala.annotation.tailrec

/**
  * Created by knabe on 28.03.17.
  */
class RecursiveListService extends ListService {

  /** Returns the minimum value from the list. If it is empty, then Int.MaxValue. */
  override def min(list: List[Int]): Int = _min(Int.MaxValue, list)

  @tailrec
  private def _min(minAccu: Int, list: List[Int]): Int =
    if (list.isEmpty) minAccu
    else {
      val head   = list.head
      val newMin = if (head < minAccu) head else minAccu
      _min(newMin, list.tail)
    }

}
