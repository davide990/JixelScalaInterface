ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

libraryDependencies += "org.apache.commons" % "commons-parent" % "53"
libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.13"
libraryDependencies += "net.liftweb" %% "lift-json" % "3.5.0"

lazy val root = (project in file("."))
  .settings(
    name := "MusaJixelInterface"
  )
