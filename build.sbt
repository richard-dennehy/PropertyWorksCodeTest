name := "PropertyWorksAPI"

version := "0.1"

scalaVersion := "2.12.3"

libraryDependencies +=  "org.scalaj" %% "scalaj-http" % "2.3.0"
libraryDependencies += "org.scalatest" %% "scalatest" % "3.0.1" % "test"
libraryDependencies += "com.typesafe.play" %% "play-json" % "2.6.3"
libraryDependencies += "com.github.tomakehurst" % "wiremock" % "1.18" % "test"