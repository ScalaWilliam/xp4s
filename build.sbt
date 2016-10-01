scalaVersion := "2.11.8"

name := "scala-xml-printing"

enablePlugins(GitVersioning)

organization := "com.scalawilliam"

libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.11.8"

libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.6"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % Test

libraryDependencies += "xmlunit" % "xmlunit" % "1.6" % Test
