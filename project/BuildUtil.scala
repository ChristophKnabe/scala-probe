import sbt.VersionNumber

object BuildUtil {

  /** Checks that the current Java version used for compilation has at least the given version number.
    * @param required the Java version required to compile and run this project.
    * @throws IllegalStateException if the current Java version is before the given version
    * @note See https://stackoverflow.com/questions/19208942/enforcing-java-version-for-scala-project-in-sbt */
  def checkJavaFrom(required: VersionNumber): Unit = {
    val current = VersionNumber(sys.props("java.specification.version"))
    println(s"==================== Java version required at least: $required. Used for compile & run: $current =======================")
    val compatible =
      current.numbers.zip(required.numbers).foldRight(required.numbers.size <= current.numbers.size)((a, b) => (a._1 > a._2) || (a._1 == a._2 && b))
    if (!compatible) {
      throw new IllegalStateException(s"Outdated JDK: java.specification.version $current < required $required");
    }
  }

}
