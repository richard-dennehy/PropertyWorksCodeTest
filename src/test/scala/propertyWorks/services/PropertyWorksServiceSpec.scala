package propertyWorks.services

import org.scalatest.{Matchers, WordSpec}
import propertyWorks.connectors.PropertyWorksAPI
import propertyWorks.models.api.{PropertyWorksData, SearchResult}
import scala.concurrent.duration._
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.{Await, ExecutionContext, Future}
import scalaj.http.HttpResponse

class PropertyWorksServiceSpec extends WordSpec with Matchers {

  "Getting the average surface area of a search" should {
    "calculate the average correctly" in {
      StubConnector.stubResults(SearchResult(Seq(PropertyWorksData(Some(1)), PropertyWorksData(Some(2)))))

      Await.result(testService.averageSurfaceArea("anywhere", 100), 2 seconds) shouldBe 1.5
    }

    "return 0 if no results have a surface area" in {
      StubConnector.stubResults(SearchResult(Seq(PropertyWorksData(None), PropertyWorksData(None))))

      Await.result(testService.averageSurfaceArea("anywhere", 100), 2 seconds) shouldBe 0
    }

    "not include results without a surface area in the average" in {
      StubConnector.stubResults(SearchResult(Seq(PropertyWorksData(Some(1)), PropertyWorksData(None))))

      Await.result(testService.averageSurfaceArea("anywhere", 100), 2 seconds) shouldBe Some(1)
    }
  }

  lazy val testService = new PropertyWorksService {
    override def connector: PropertyWorksAPI = StubConnector
  }

  private object StubConnector extends PropertyWorksAPI {
    private var fakeResults: SearchResult = _
    override def baseUrl: String = ???

    override def search(location: String, limit: Int)(implicit ec: ExecutionContext): Future[HttpResponse[SearchResult]] = Future.successful {
      HttpResponse(fakeResults, 200, Map())
    }

    def stubResults(results: SearchResult): Unit = {
      fakeResults = results
    }
  }
}
