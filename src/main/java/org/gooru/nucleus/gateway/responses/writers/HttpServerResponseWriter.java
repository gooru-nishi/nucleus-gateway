package org.gooru.nucleus.gateway.responses.writers;

import io.vertx.core.AsyncResult;
import io.vertx.core.eventbus.Message;
import io.vertx.core.http.HttpServerResponse;
import io.vertx.ext.web.RoutingContext;
import org.gooru.nucleus.gateway.constants.HttpConstants;
import org.gooru.nucleus.gateway.responses.transformers.ResponseTransformer;
import org.gooru.nucleus.gateway.responses.transformers.ResponseTransformerBuilder;

import java.util.Map;

class HttpServerResponseWriter implements ResponseWriter {

  private final RoutingContext routingContext;
  private final AsyncResult<Message<Object>> message;

  public HttpServerResponseWriter(RoutingContext routingContext, AsyncResult<Message<Object>> message) {
    this.routingContext = routingContext;
    this.message = message;
  }

  @Override
  public void writeResponse() {
    ResponseTransformer transformer = ResponseTransformerBuilder.build(message.result());
    final HttpServerResponse response = routingContext.response();
    // First set the status code
    response.setStatusCode(transformer.transformedStatus());
    // Then set the headers
    Map<String, String> headers = transformer.transformedHeaders();
    if (headers != null && !headers.isEmpty()) {
      // Never accept content-length from others, we do that
      headers.keySet().stream().filter(headerName -> !headerName.equalsIgnoreCase(HttpConstants.HEADER_CONTENT_LENGTH))
             .forEach(headerName -> response.putHeader(headerName, headers.get(headerName)));
    }
    // Then it is turn of the body to be set and ending the response
    final String responseBody =
      ((transformer.transformedBody() != null) && (!transformer.transformedBody().isEmpty())) ? transformer.transformedBody().toString() : null;
    if (responseBody != null) {
      response.putHeader(HttpConstants.HEADER_CONTENT_LENGTH, Integer.toString(responseBody.length()));
      response.end(responseBody);
    } else {
      response.end();
    }
  }
}
