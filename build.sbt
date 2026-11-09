scalaVersion := "3.9.0"

lazy val root = rootProject
  .settings(
    name := "RiderData",
    libraryDependencies ++= Seq(
      //You can add library dependencies here, for example,
      //"org.scalatest" %% "scalatest" % "3.2.19" % Test,
      "org.scalameta" %% "munit" % "1.3.6" % Test,
      "com.github.tototoshi" %% "scala-csv" % "2.0.0"
    )
  )
