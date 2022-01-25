package recursion

/**
  * Created by knabe on 27.03.17.
  */
class IterativeListService extends ListService {

  override def min(list: List[Int]): Int = {
    var result = Int.MaxValue
    var currentList = list
    while (currentList != Nil) {
      val value = currentList.head
      if (value < result) {
        result = value
      }
      currentList = currentList.tail
    }
    result
  }

}
