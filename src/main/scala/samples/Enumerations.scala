package samples

/** Demonstration of different approaches for modelling enumeration types in Scala.
  * The approaches start from the simplest and proceed to more complicated ones.
  * The parameter of the `show` method demonstrates usage of the enumeration type.
  * The `for` loop iterates over all known values.
  * As the domain concept to model we use a JobStatus which can have the values
  * `planned`, `running`, and `completed`.
  * See also a discussion at https://underscore.io/blog/posts/2014/09/03/enumerations.html
  *
  * @author Christoph Knabe
  * @since 2021-06-22 */
object Enumerations extends App {

  // a) Classical Scala Enumeration
  // How: Use the classical Scala Enumeration base class
  // Pro: Values are ordered and iterable.
  // Pro: Very compact notation
  // Pro: Efficient mapping to integers
  // Contra: Difficult to understand (Value is moving)
  // Contra: Unnice type naming (JobStatus.Value)
  // Contra: Type is erased at runtime
  object ClassicalScalaEnumeration {

    object JobStatus extends Enumeration {
      val planned, running, completed = Value
    }

    def show(value: JobStatus.Value): Unit = println(s"value=$value")
  }

  println(ClassicalScalaEnumeration.getClass.getSimpleName)
  for (v <- ClassicalScalaEnumeration.JobStatus.values) {
    ClassicalScalaEnumeration.show(v)
  }

  // b) Define a domain type, some possible values, and a collection of them.
  // How: Simply use a case class, define values as val.
  // Pro: Type-safely distinguish different domain types
  // Pro: Technically uses only 1 class
  object SimpleDomainTypes {
    case class JobStatus(name: String)

    object JobStatus {
      val planned = JobStatus("planned")
      val running = JobStatus("running")
      val completed = JobStatus("completed")
      val values = List(planned, running, completed)
    }

    def show(value: JobStatus): Unit = println(s"value=$value")
    val additional = JobStatus("additional") //This is problematic.

  }

  println(SimpleDomainTypes.getClass.getSimpleName)
  for (v <- SimpleDomainTypes.JobStatus.values) {
    SimpleDomainTypes.show(v)
  }

  // c) Define a domain type with attributes, and all possible values.
  // How: Use a sealed abstract marker trait with values as case objects.
  // Pro: Type-safely distinguish different domain types
  // Pro: Only the values in the same file can be constructed.
  // Pro: Compiler can warn if match is not exhaustive
  // Contra: Technically uses n+1 classes (abstract class and all objects)
  object ExhaustiveValuesDomainTypesSimple {
    sealed abstract trait JobStatus

    object JobStatus {
      case object planned extends JobStatus
      case object running extends JobStatus
      case object completed extends JobStatus
      val values = List(planned, running, completed)
    }

    def show(value: JobStatus): Unit = println(s"value=$value")
    case object additional extends JobStatus //not compilable in other file

  }

  println(ExhaustiveValuesDomainTypesSimple.getClass.getSimpleName)
  for (v <- ExhaustiveValuesDomainTypesSimple.JobStatus.values) {
    ExhaustiveValuesDomainTypesSimple.show(v)
  }

  // d) Define a domain type with attributes, and all possible values.
  // How: Use a sealed abstract type with private constructor and values as case objects.
  // Pro: Type-safely distinguish different domain types
  // Pro: Only the values in the same file can be constructed.
  // Pro: Compiler can warn if match is not exhaustive
  // Contra: Technically uses n+1 classes (abstract class and all objects)
  object ExhaustiveValuesDomainTypesWithAttributes {
    sealed abstract class JobStatus private (val name: String)

    object JobStatus {
      case object planned extends JobStatus("planned")
      case object running extends JobStatus("running")
      case object completed extends JobStatus("completed")
      val values = List(planned, running, completed)
    }

    def show(value: JobStatus): Unit = println(s"value=$value")
    //case object additional extends JobStatus("additional") //not compilable

  }

  println(ExhaustiveValuesDomainTypesWithAttributes.getClass.getSimpleName)
  for (v <- ExhaustiveValuesDomainTypesWithAttributes.JobStatus.values) {
    ExhaustiveValuesDomainTypesWithAttributes.show(v)
  }

}
