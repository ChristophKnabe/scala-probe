import sbt.Level
//Christoph Knabe, 2017-03-29

//Zwischen einzelnen Schlüsseldefinitionen mit := immer eine Leerzeile lassen!

name := "scala-probe"

version := "1.0"

scalaVersion := "2.13.2"

logLevel := Level.Info

addCompilerPlugin(scalafixSemanticdb("4.4.4"))

val akkaVersion = "2.6.6"
val mockitoScalaVersion = "1.14.8"

libraryDependencies ++= Seq(
  "com.novocode" % "junit-interface" % "0.11" % "test",
  //"org.scalatest" %% "scalatest" % "3.0.8" % "test",
  //"org.scalatestplus" %% "scalatestplus-scalacheck" % "3.1.0.0-RC2" % Test,
  //See https://www.scalatest.org/plus/scalacheck
  "org.scalatestplus" %% "scalacheck-1-15" % "3.2.10.0" % Test,
  "org.scalatest" %% "scalatest-funsuite" % "3.2.10" % Test,
  "org.scalatest" %% "scalatest-funspec" % "3.2.10" % Test,
  "org.scalatest" %% "scalatest-flatspec" % "3.2.10" % Test,
  "org.scalatest" %% "scalatest-mustmatchers" % "3.2.10" % Test,
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  //See https://github.com/rickynils/scalacheck/blob/master/examples/simple-sbt/build.sbt
  //Included by "org.scalatestplus" %% "scalacheck-1-15":
  //"org.scalacheck" %% "scalacheck" % "1.14.0" % "test",
  "org.mockito" %% "mockito-scala" % mockitoScalaVersion % Test,
  "org.mockito" %% "mockito-scala-scalatest" % mockitoScalaVersion % Test
  /*The operator %% builds an artifact name from the specified scalaVersionDependentArtifact name,
   * an underscore sign, and the upper mentioned scalaVersion.
   * So the artifact name will result here in e.g. scalatest_2.13,
   * as the last number in a Scala version is not API relevant.
   */
)

scalacOptions in ThisBuild ++= Seq(
  "-deprecation",
  "-unchecked",
  "-language:_",
  "--explain-types",
  "-Xfatal-warnings",
  "-Yrangepos",
  "-Wunused"
)

//See http://www.scalatest.org/user_guide/using_scalatest_with_sbt
logBuffered in Test := false

//Used for ScalaCheck, see https://github.com/rickynils/scalacheck/blob/master/examples/simple-sbt/build.sbt
testOptions in Test += Tests.Argument(TestFrameworks.ScalaCheck, "-maxSize", "5", "-minSuccessfulTests", "33", "-workers", "1", "-verbosity", "1")

//Tell the SBT Eclipse plugin to download all sources along with binary .jar files and make them available for source code navigation. Only if the SBT Eclipse plugin is activated:
//EclipseKeys.withSource := true

// Scala style checker https://github.com/sksamuel/sbt-scapegoat
// transforms style violations to compile errors or warnings during build time:
scapegoatVersion in ThisBuild := "1.4.4"
// Configure by:
// scapegoatDisabledInspections := Seq("ExpressionAsStatement", "VarUse")
// scapegoatIgnoredFiles := Seq(".*/SomeScala.scala")

scalafixDependencies in ThisBuild += "com.github.vovapolu" %% "scaluzzi" % "0.1.16"

initialize := {
  val _ = initialize.value // run the previous initialization
  BuildUtil.checkJavaFrom(VersionNumber("10")) //For URLDecoder.decode("abc%20def", utf8Charset)
}
