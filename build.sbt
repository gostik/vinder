name := """play2-crud-activator"""

version := "0.7.3-SNAPSHOT"

libraryDependencies ++= Seq(
  javaCore, javaJdbc, javaEbean,
  "play2-crud" % "play2-crud_2.10" % "0.7.3-SNAPSHOT",
  "com.typesafe" %% "play-plugins-redis" % "2.2.1",
  "org.atmosphere" % "atmosphere-runtime" % "2.0.0.RC3"
)

play.Project.playJavaSettings

resolvers += "release repository" at  "http://hakandilek.github.com/maven-repo/releases/"

resolvers += "snapshot repository" at "http://hakandilek.github.com/maven-repo/snapshots/"

resolvers += "Sedis repository" at "http://pk11-scratch.googlecode.com/svn/trunk/"
