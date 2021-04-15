package probe.causechain

import java.io.FileNotFoundException

import scala.annotation.tailrec

/** Demonstrates the definition and reporting of a chain of reportable errors unifying Scala and Java worlds.
  *
  * @author Christoph Knabe
  * @since 2021-03-31 */
@SuppressWarnings(
  Array(
    "scalafix:Disable.SuppressWarnings",
    "scalafix:Disable.FileNotFoundException",
    "scalafix:Disable.RuntimeException",
    "scalafix:Disable.getClass",
    "scalafix:Disable.toString",
    "scalafix:Disable.getCause"
  )
)
object ClassWithValDemo {

  /** Reportable error with causal chain. The attribute values must be passed at construction time. This prevents a circular causal chain.
    * @param cause the [[Reportable]] which has caused this [[Reportable]]. It can not be overridden in order to prevent circular causal chains. */
  abstract class Reportable(val message: String, final val cause: Option[Reportable] = None) {

    /** Returns the `message` of the object prefixed by its class name. */
    def namedMessage: String = s"${getClass.getName}: $message"
  }

  /** A wrapper object for a Java [[Throwable]].
    * @param throwable to be wrapped, should not be null.
    * @return the wrapped [[Throwable]]. If it was null, a dummy object is returned. */
  final case class FromThrowable(throwable: Throwable)
  extends Reportable(
    Option(throwable).map(_.toString).getOrElse("(null: Throwable)"),
    Option(throwable).flatMap(th => FromThrowable.wrap(th.getCause))
  )

  object FromThrowable {

    /** @param throwable to be wrapped, may be null.*/
    def wrap(throwable: Throwable): Option[FromThrowable] = Option(throwable).map(FromThrowable(_))
  }

  final case class FileNotFound(filename: String, causedBy: Throwable) extends Reportable(s"Cannot find file $filename", FromThrowable.wrap(causedBy))

  final case class FileCopyFailure(source: String, dest: String, causedBy: Reportable)
  extends Reportable(s"File $source could not be copied to $dest", Some(causedBy))

  def causesMessage(reportable: Reportable): String = {
    toReverseCauseList(reportable, Nil).reverseIterator.map(_.namedMessage).mkString("\nCaused by ")
  }

  /** Builds a reverse List of the [[Reportable]] and its causal chain by following the `.cause` link until `None` is reached. */
  @tailrec
  private def toReverseCauseList(causalChain: Reportable, accu: List[Reportable]): List[Reportable] = {
    val newAccu = causalChain :: accu
    causalChain.cause match {
      case None => newAccu
      case Some(tailCausalChain) => toReverseCauseList(tailCausalChain, newAccu)
    }
  }

  def main(args: Array[String]): Unit = {
    val fnfe = new FileNotFoundException
    val rte = new RuntimeException("Could not open file", fnfe)
    val fnf = FileNotFound("/home/knabe/abc.txt", rte)
    val fcf = FileCopyFailure("abc.txt", "def.txt", fnf)
    //Print the messages of the causal chain:
    println(causesMessage(fcf))

    //Demonstrate pattern matching works on Reportable objects:
    def print(reportable: Reportable) =
      reportable match {
        case FileCopyFailure(source, dest, causedBy) => println(s"FileCopyFailure matched with source=$source, dest=$dest, causedBy=$causedBy")
        case FileNotFound(file, causedBy) => println(s"FileNotFound matched with file=$file, causedBy=$causedBy")
      }
    print(fcf)
    print(fnf)
  }

}
