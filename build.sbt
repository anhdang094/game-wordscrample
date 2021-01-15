name := """computer-database-java"""

version := "1.0-SNAPSHOT"

scalaVersion := "2.11.7"
libraryDependencies += evolutions
libraryDependencies ++= Seq(
  jdbc,
  javaWs,
  javaJpa,
  "mysql" % "mysql-connector-java" % "5.1.36",
  "org.hibernate" % "hibernate-entitymanager" % "4.3.1.Final",
  "org.webjars" % "jquery" % "3.0.0",
  "org.webjars" % "bootstrap" % "3.3.6",
  "org.mindrot" % "jbcrypt" % "0.3m"
)     

lazy val root = (project in file(".")).enablePlugins(PlayJava, PlayEbean)


fork in run := true