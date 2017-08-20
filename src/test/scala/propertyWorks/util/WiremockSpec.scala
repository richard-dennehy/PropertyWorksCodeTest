package propertyWorks.util

import com.github.tomakehurst.wiremock.WireMockServer
import com.github.tomakehurst.wiremock.client.WireMock
import org.scalatest.{BeforeAndAfterAll, BeforeAndAfterEach, TestSuite, WordSpecLike}

trait WiremockSpec extends TestSuite with BeforeAndAfterAll with BeforeAndAfterEach {
  private lazy val wiremockPort = 8080
  private lazy val wiremockServer = new WireMockServer(wiremockPort)

  lazy val mockServerUrl = s"http://localhost:$wiremockPort"

  override protected def beforeAll(): Unit = {
    super.beforeAll()
    wiremockServer.start()
    WireMock.configureFor("localhost", wiremockPort)
  }

  override protected def beforeEach(): Unit = {
    super.beforeEach()
    WireMock.reset()
  }

  override protected def afterAll(): Unit = {
    super.afterAll()
    wiremockServer.stop()
  }
}
