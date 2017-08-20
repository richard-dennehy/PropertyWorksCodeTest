package propertyWorks.connectors

import com.github.tomakehurst.wiremock.client.WireMock._
import org.scalatest.{Matchers, WordSpec}
import propertyWorks.models.api.{PropertyWorksData, SearchResult}
import propertyWorks.util.WiremockSpec

import scala.concurrent.Await
import scala.concurrent.ExecutionContext.Implicits.global
import scala.concurrent.duration._

class PropertyWorksAPISpec extends WordSpec with Matchers with WiremockSpec {

  "Searching the API" should {
    "parse the response JSON" in {
      stubFor(get(urlEqualTo("/properties/search?location=brighton&limit=50"))
        .willReturn(aResponse()
          .withStatus(200)
          .withBody(responseJson)
          .withHeader("Content-Type", "application/json")
        )
      )

      val res = Await.result(testAPI.search("brighton", 50), 2 seconds)
      res.code shouldBe 200
      res.body shouldBe SearchResult(Seq(PropertyWorksData(Some(5000))))
    }

    "handle entries with no surface area" in {
      stubFor(get(urlEqualTo("/properties/search?location=notBrighton&limit=500"))
        .willReturn(aResponse()
          .withStatus(200)
          .withBody(noSurfaceAreaJson)
          .withHeader("Content-Type", "application/json")
        )
      )

      val res = Await.result(testAPI.search("notBrighton", 500), 2 seconds)
      res.code shouldBe 200
      res.body shouldBe SearchResult(Seq(PropertyWorksData(None)))
    }
  }

  lazy val testAPI = new PropertyWorksAPI {
    override lazy val baseUrl: String = mockServerUrl
  }

  lazy val responseJson: String =
    """
      |{
      |  "total": 1,
      |  "offset": 0,
      |  "limit": 50,
      |  "data": [{
      |      "id": 31732,
      |      "slug": "chapel-studios-off-waterloo-street-brighton-hove-brighton-bn3-1ar",
      |      "agency": {
      |        "id": 2,
      |        "name": "Instant Offices",
      |        "slug": "instantoffices",
      |        "agencyType": "agency"
      |      },
      |      "address": {
      |        "lat": 50.8235,
      |        "name": "Chapel Studios",
      |        "address": "Off Waterloo Street, Brighton & Hove, Brighton",
      |        "postcode": "BN3 1AR",
      |        "lon": -0.156434
      |      },
      |      "images": [
      |        {
      |          "url": "//propertyworks.imgix.net/instantoffices/chapel-studios-off-waterloo-street-brighton-hove-brighton-east-sussex-bn3-1ar-585890496--320x214.jpeg"
      |        },
      |        {
      |          "url": "//propertyworks.imgix.net/instantoffices/chapel-studios-off-waterloo-street-brighton-hove-brighton-east-sussex-bn3-1ar--1331352011--320x214.jpeg"
      |        },
      |        {
      |          "url": "//propertyworks.imgix.net/instantoffices/chapel-studios-off-waterloo-street-brighton-hove-brighton-east-sussex-bn3-1ar-701873885--320x214.jpeg"
      |        },
      |        {
      |          "url": "//propertyworks.imgix.net/instantoffices/chapel-studios-off-waterloo-street-brighton-hove-brighton-east-sussex-bn3-1ar--1525919177--320x214.jpeg"
      |        },
      |        {
      |          "url": "//propertyworks.imgix.net/instantoffices/chapel-studios-off-waterloo-street-brighton-hove-brighton-east-sussex-bn3-1ar-1001765308--320x214.jpeg"
      |        },
      |        {
      |          "url": "//propertyworks.imgix.net/instantoffices/chapel-studios-off-waterloo-street-brighton-hove-brighton-east-sussex-bn3-1ar--1463915255--320x214.jpeg"
      |        },
      |        {
      |          "url": "//propertyworks.imgix.net/instantoffices/chapel-studios-off-waterloo-street-brighton-hove-brighton-east-sussex-bn3-1ar-1789390248--320x214.jpeg"
      |        },
      |        {
      |          "url": "//propertyworks.imgix.net/instantoffices/chapel-studios-off-waterloo-street-brighton-hove-brighton-east-sussex-bn3-1ar-969476485--320x214.jpeg"
      |        }
      |      ],
      |      "status": "available",
      |      "tenure": "let",
      |      "propertyType": "office",
      |      "propertyTypes": [
      |        "office"
      |      ],
      |      "urlLocationData": {
      |        "ward": "brighton-and-hove-brunswick-and-adelaide",
      |        "city": "brighton-and-hove"
      |      },
      |      "propertySubtype": "serviced-office",
      |      "priceMonSqft": 2.5,
      |      "surfaceSqft": 5000,
      |      "capacityPeople": 50,
      |      "descriptionPreview": "This centre offers individual desk space ideal for start-ups, small businesses and individual freela...",
      |      "priceAndSize": {
      |        "displayPrice": {
      |          "value": 250,
      |          "valueMin": 250,
      |          "valueMax": 300,
      |          "unit": "people",
      |          "frequency": "month",
      |          "display": "From £250 PPM"
      |        },
      |        "displaySize": {
      |          "value": 50,
      |          "valueMin": 1,
      |          "valueMax": 50,
      |          "unit": "people",
      |          "display": "Up to 50 desks"
      |        },
      |        "estimatedPrice": {
      |          "value": 250,
      |          "valueMin": 250,
      |          "unit": "people",
      |          "frequency": "month",
      |          "display": "From £250 PPM"
      |        }
      |      },
      |      "title": "Chapel Studios",
      |      "score": 12.041615,
      |      "sponsored": false,
      |      "modified": false,
      |      "modifiedDate": "2017-08-20T05:24:17Z",
      |      "premium": false,
      |      "useClasses": []
      |    }]
      | }
    """.stripMargin

  lazy val noSurfaceAreaJson: String =
    """
      |{
      |  "total": 1,
      |  "offset": 0,
      |  "limit": 50,
      |  "data": [{
      |      "id": 31733,
      |      "slug": "tower-point-44-north-road-brighton-bn1-1yr",
      |      "agency": {
      |        "id": 2,
      |        "name": "Instant Offices",
      |        "slug": "instantoffices",
      |        "agencyType": "agency"
      |      },
      |      "address": {
      |        "lat": 50.8258,
      |        "name": "Tower Point 44",
      |        "address": "North Road, Brighton",
      |        "postcode": "BN1 1YR",
      |        "lon": -0.141646
      |      },
      |      "images": [
      |        {
      |          "url": "//propertyworks.imgix.net/instantoffices/tower-point-44-north-road-brighton-east-sussex-bn1-1yr--499805761--550x411.jpeg"
      |        },
      |        {
      |          "url": "//propertyworks.imgix.net/instantoffices/tower-point-44-north-road-brighton-east-sussex-bn1-1yr--1995561494--550x411.jpeg"
      |        },
      |        {
      |          "url": "//propertyworks.imgix.net/instantoffices/tower-point-44-north-road-brighton-east-sussex-bn1-1yr--1899003508--550x411.jpeg"
      |        },
      |        {
      |          "url": "//propertyworks.imgix.net/instantoffices/tower-point-44-north-road-brighton-east-sussex-bn1-1yr-1894199177--550x411.jpeg"
      |        }
      |      ],
      |      "status": "available",
      |      "tenure": "let",
      |      "propertyType": "office",
      |      "propertyTypes": [
      |        "office"
      |      ],
      |      "urlLocationData": {
      |        "ward": "brighton-and-hove-st-peter-s-and-north-laine",
      |        "city": "brighton-and-hove"
      |      },
      |      "priceMonSqft": 0.27,
      |      "descriptionPreview": "Fully refurbished to the highest specification the building has all you would expect from a modern o...",
      |      "priceAndSize": {
      |        "displayPrice": {
      |          "value": 27,
      |          "valueMin": 27,
      |          "unit": "people",
      |          "frequency": "month",
      |          "display": "From £27 PPM"
      |        },
      |        "estimatedPrice": {
      |          "value": 5.460000000000001,
      |          "valueMin": 5.460000000000001,
      |          "unit": "sqft",
      |          "frequency": "year",
      |          "display": "From £5.46 per ft²"
      |        }
      |      },
      |      "title": "Tower Point 44",
      |      "score": 12.107731,
      |      "sponsored": true,
      |      "modified": false,
      |      "modifiedDate": "2017-08-19T05:25:24Z",
      |      "validation": "warn",
      |      "premium": false,
      |      "useClasses": []
      |    }
      | ]}
    """.stripMargin
}
