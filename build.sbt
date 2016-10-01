scalaVersion := "2.11.8"

name := "xp4s"

enablePlugins(GitVersioning)

organization := "xp4s"

libraryDependencies += "org.scala-lang" % "scala-reflect" % "2.11.8"

libraryDependencies += "org.scala-lang.modules" %% "scala-xml" % "1.0.6"

libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.0" % Test

libraryDependencies += "xmlunit" % "xmlunit" % "1.6" % Test

licenses += ("MIT", url("http://opensource.org/licenses/MIT"))

git.useGitDescribe := true
