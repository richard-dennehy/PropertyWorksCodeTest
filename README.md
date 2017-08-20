# Property.Works Scala coding assignment

Makes an API call to `search.property.works/properties/search` and calculates the average surface area of the listed properties.

To run:

_sbt run_

## Library dependencies

### Play JSON
Uses Play JSON for the `Json.parse` method and the `Json.format` macro.

In short, the `Json.format` macro automatically generates a JSON reader/writer from a case class, where the field names and the types match the definition of the case class.

This means extending the project to read more data from the search results only requires adding the new field to the case class.

It can also automatically generate Option and Seq formatters, for nullable fields and arrays respectively.

### Simplified Http
The scalaj Simplified HTTP library is used for HTTP calls, as it is a simple standalone library. 

The main downside of this library is that it is blocking, however, this application will have to block on the result eventually.

This blocking has been pushed into the main method, and the invocation of HTTP within the API Connector is wrapped in a Future.

In a larger/more complicated system, I would prefer to use Play WS. This was not used here as it is a more complicated library, and requires an Akka actor system and other configuration.

### Wiremock
Wiremock is used as part of the testing, to run a fake HTTP server for the connector to use.
