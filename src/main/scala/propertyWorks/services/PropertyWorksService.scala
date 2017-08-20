package propertyWorks.services

import propertyWorks.connectors.PropertyWorksAPI
import propertyWorks.models.api.PropertyWorksData

import scala.concurrent.{ExecutionContext, Future}

trait PropertyWorksService {
  def connector: PropertyWorksAPI

  protected def search(location: String, limit: Int)(implicit ec: ExecutionContext): Future[Seq[PropertyWorksData]] = {
    connector.search(location, limit) map { res =>
      res.body.data
    }
  }

  def averageSurfaceArea(location: String, limit: Int)(implicit ec: ExecutionContext): Future[BigDecimal] = {
    search(location, limit) map { data => avgSurfaceAreaOf(data) }
  }

  private def avgSurfaceAreaOf(data: Seq[PropertyWorksData]): BigDecimal = {
    //counts the total surface area, and the number of results with a surface area, in a single iteration of the list
    val (total, count) = data.foldLeft[(BigDecimal, Int)]((0, 0)) {
      case ((t, c), PropertyWorksData(Some(sqft))) => (t + sqft, c + 1)
      //if the data does not include a surface area, do not include it in the average
      case ((t, c), _) => (t, c)
    }

    if (count == 0) 0 else total / count
  }
}

object PropertyWorksService extends PropertyWorksService {
  lazy val connector: PropertyWorksAPI = PropertyWorksAPI
}
