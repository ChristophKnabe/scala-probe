package recursion

/**
  * Created by knabe on 27.03.17.
  */
trait ListService {

  def name = getClass.getSimpleName

  /**Returns the minimum value from the list. If it is empty, then Int.MaxValue.*/
  def min(list: List[Int]): Int

}
