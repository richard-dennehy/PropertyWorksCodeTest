package propertyWorks.models.api

import play.api.libs.json.{Format, Json}

case class PropertyWorksData(surfaceSqft: Option[BigDecimal])

object PropertyWorksData {
  implicit val format: Format[PropertyWorksData] = Json.format[PropertyWorksData]
}