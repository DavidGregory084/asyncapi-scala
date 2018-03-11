scalaVersion := "2.12.4"

name := "asyncapi-scala"

organization := "io.github.davidgregory084"

version := "0.1.0-SNAPSHOT"

libraryDependencies ++= "io.circe" %% "circe-derivation" % "0.9.0-M1" +: Seq(
  "io.circe" %% "circe-core",
  "io.circe" %% "circe-generic",
  "io.circe" %% "circe-parser",
  "io.circe" %% "circe-refined"
).map(_ % "0.9.1")

libraryDependencies ++= Seq(
  "org.typelevel" %% "cats-core" % "1.0.1",
  "eu.timepit" %% "refined" % "0.8.7",
  "com.typesafe.scala-logging" %% "scala-logging" % "3.8.0",
  "ch.qos.logback" % "logback-classic" % "1.2.3" % Runtime,
  "org.typelevel" %% "cats-testkit" % "1.0.1" % Test,
  "eu.timepit" %% "refined-scalacheck" % "0.8.7" % Test
)
