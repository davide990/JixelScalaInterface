ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.10"

libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.14"
//libraryDependencies += "net.liftweb" %% "lift-json" % "3.5.0" not working
//libraryDependencies += "net.liftweb" %% "lift-json" % "3.0.1" // working with scala 2.12
libraryDependencies += "net.liftweb" %% "lift-json" % "3.4.0"
libraryDependencies += "com.rabbitmq" % "amqp-client" % "5.16.0"
libraryDependencies ++= Seq("org.slf4j" % "slf4j-api" % "2.0.5",
  "org.slf4j" % "slf4j-simple" % "2.0.5")

lazy val root = (project in file("."))
  .settings(
    name := "MusaJixelInterface"
  )



