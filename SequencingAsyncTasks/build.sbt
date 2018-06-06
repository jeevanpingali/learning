name := "SequencingAsyncTasks"

version := "1.0"

scalaVersion := "2.12.2"

lazy val akkaVersion = "2.5.12"

libraryDependencies ++= Seq(
  "com.typesafe.akka" %% "akka-actor" % akkaVersion,
  "com.typesafe.akka" %% "akka-testkit" % akkaVersion,
  "junit" % "junit" % "4.12")

libraryDependencies += "com.typesafe.akka" %% "akka-slf4j" % "2.5.12"

libraryDependencies += "com.typesafe.akka" %% "akka-cluster" % "2.5.12"

libraryDependencies += "ch.qos.logback" % "logback-classic" % "1.2.3"
