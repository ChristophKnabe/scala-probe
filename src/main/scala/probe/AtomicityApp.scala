package probe

object AtomicityApp extends App {

  private var uidCount = 0L

  def getUniqueId(): Long =
    this.synchronized { //Make the body of this method atomic!
      uidCount = uidCount + 1
      uidCount
    }

  def startThread() = {
    val t = new Thread {
      override def run(): Unit = {
        val uids = for (_ <- 0 until 50) yield getUniqueId()
        println(uids)
      }
    }
    t.start()
  }

  startThread()
  startThread()

}
