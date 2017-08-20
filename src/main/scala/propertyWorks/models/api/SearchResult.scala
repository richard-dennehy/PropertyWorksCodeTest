package propertyWorks.models.api

import play.api.libs.json.{Format, Json}

case class SearchResult(data: Seq[PropertyWorksData])

object SearchResult {
  implicit val format: Format[SearchResult] = Json.format[SearchResult]
}