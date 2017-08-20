package propertyWorks

import propertyWorks.services.PropertyWorksService

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._
import scala.language.postfixOps
import scala.util.{Failure, Success, Try}

object Main extends App {
  override def main(args: Array[String]): Unit = {
    Try { Await.result(PropertyWorksService.averageSurfaceArea("brighton", 50), 20 seconds) } match {
      case Success(avg) => println(avg)
      case Failure(t) => println(s"An error occurred: $t")
    }
  }
}
