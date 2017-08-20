package propertyWorks.connectors

import play.api.libs.json.Json
import propertyWorks.models.api.SearchResult

import scala.concurrent.{ExecutionContext, Future}
import scalaj.http.{Http, HttpResponse}

trait PropertyWorksAPI {
  def baseUrl: String

  def search(location: String, limit: Int)(implicit ec: ExecutionContext): Future[HttpResponse[SearchResult]] = Future {
    Http(s"$baseUrl/properties/search?location=$location&limit=$limit").execute { inputStream =>
      Json.parse(inputStream).as[SearchResult]
    }
  }

}

object PropertyWorksAPI extends PropertyWorksAPI {
  lazy val baseUrl: String = "https://search.property.works"
}
