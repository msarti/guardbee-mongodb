import sbt._
import Keys._
import PlayProject._

object ApplicationBuild extends Build {

  val appName = "guardbee-mongodb"
  val appVersion = "master-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    "guardbee" %% "guardbee" % "master",
    "com.novus" %% "salat" % "1.9.2-SNAPSHOT",
    "se.radley" %% "play-plugins-salat" % "1.2")

  val main = play.Project(appName, appVersion, appDependencies).settings(
    routesImport += "se.radley.plugin.salat.Binders._",
    templatesImport += "org.bson.types.ObjectId",
      resolvers ++= Seq (Resolver.url("sbt-plugin-snapshots", new URL("http://repo.scala-sbt.org/scalasbt/sbt-plugin-snapshots/"))(Resolver.ivyStylePatterns),
        "Typesafe repository" at "http://repo.typesafe.com/typesafe/releases/", "Sonatype OSS Snapshots" at "https://oss.sonatype.org/content/repositories/snapshots"))

}
