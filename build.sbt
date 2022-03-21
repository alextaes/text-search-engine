ThisBuild / version := "1.0.0"

ThisBuild / scalaVersion := "2.12.15"

lazy val root = (project in file("."))
  .settings(
    name := "text-search-engine"
  )

libraryDependencies += "org.apache.spark" %% "spark-core" % "3.2.1"
