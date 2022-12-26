ThisBuild / version := "0.1.0-SNAPSHOT"

ThisBuild / scalaVersion := "2.13.8"

libraryDependencies += "org.apache.httpcomponents" % "httpclient" % "4.5.13"
libraryDependencies += "net.liftweb" %% "lift-json" % "3.5.0"
libraryDependencies += "com.rabbitmq" % "amqp-client" % "5.16.0"
libraryDependencies ++= Seq("org.slf4j" % "slf4j-api" % "2.0.3",
  "org.slf4j" % "slf4j-simple" % "2.0.3")

lazy val root = (project in file("."))
  .settings(
    name := "MusaJixelInterface"
  )



