import sbt._
import Keys._
import play.Project._

object ApplicationBuild extends Build {

  val appName         = "Neuve"
  val appVersion      = "1.0-SNAPSHOT"

  val appDependencies = Seq(
    // Add your project dependencies here,
    javaCore,
    javaJdbc,
    cache,
    "org.mybatis" % "mybatis" % "3.1.1",
    "org.mybatis" % "mybatis-guice" % "3.3",
    "com.orangesignal" % "orangesignal-csv" % "2.1.0",
	"commons-io" % "commons-io" % "2.4"
  )

  val main = play.Project(appName, appVersion, appDependencies).settings(
    // Add your own project settings here      
    // Add app folder as resource directory so that mapper xml files are in the classpath
    unmanagedResourceDirectories in Compile <+= baseDirectory( _ / "app" ),
    // but filter out java and html files that would then also be copied to the classpath
    excludeFilter in Compile in unmanagedResources := "*.java" || "*.html",
    
     Keys.javaOptions in (Test) += "-Xrunjdwp:transport=dt_socket,server=y,suspend=n,address=9998"
  )

}
