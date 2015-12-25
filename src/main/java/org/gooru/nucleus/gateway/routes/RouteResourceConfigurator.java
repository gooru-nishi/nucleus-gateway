package org.gooru.nucleus.gateway.routes;

import org.gooru.nucleus.gateway.constants.ConfigConstants;
import org.gooru.nucleus.gateway.constants.MessageConstants;
import org.gooru.nucleus.gateway.constants.MessagebusEndpoints;
import org.gooru.nucleus.gateway.constants.RouteConstants;
import org.gooru.nucleus.gateway.responses.writers.ResponseWriterBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.http.HttpMethod;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;

class RouteResourceConfigurator implements RouteConfigurator {

  static final Logger LOG = LoggerFactory.getLogger("org.gooru.nucleus.gateway.bootstrap.ServerVerticle");

  @Override
  public void configureRoutes(Vertx vertx, Router router, JsonObject config) {
    
    final EventBus eb = vertx.eventBus();

    final long mbusTimeout = config.getLong(ConfigConstants.MBUS_TIMEOUT, 30L);

    
    router.get(RouteConstants.EP_RESOURCE_GET).handler(routingContext -> {
      String resourceId = routingContext.request().getParam(RouteConstants.ID_RESOURCE);
      DeliveryOptions options = new DeliveryOptions().setSendTimeout(mbusTimeout*1000).addHeader(MessageConstants.MSG_HEADER_OP, MessageConstants.MSG_OP_RES_GET)
              .addHeader(RouteConstants.ID_RESOURCE, resourceId);
      eb.send(MessagebusEndpoints.MBEP_RESOURCE, getBodyForMessage(routingContext), options, reply -> {
        if (reply.succeeded()) {
          new ResponseWriterBuilder(routingContext, reply).build().writeResponse();
        } else {
          LOG.error("Not able to send message", reply.cause());
          routingContext.response().setStatusCode(500).end();
        }
      });
    });
    
    router.post(RouteConstants.EP_RESOURCE_CREATE).handler(routingContext -> {
      DeliveryOptions options = new DeliveryOptions().setSendTimeout(mbusTimeout*1000).addHeader(MessageConstants.MSG_HEADER_OP, MessageConstants.MSG_OP_RES_CREATE);
      eb.send(MessagebusEndpoints.MBEP_RESOURCE, getBodyForMessage(routingContext), options, reply -> {
        if (reply.succeeded()) {
          // TODO: Even if we got a response, we need to render it correctly as we may have to send the errors or exceptions
          routingContext.response().end(reply.result().body().toString());
        } else {
          LOG.error("Not able to send message", reply.cause());
          routingContext.response().setStatusCode(500).end();
        }
      });      
    });
    
    
    router.put(RouteConstants.EP_RESOURCE_UPDATE).handler(routingContext -> {
      
    });
  }
  
  private JsonObject getBodyForMessage(RoutingContext routingContext) {
    JsonObject result = new JsonObject();
    JsonObject httpBody = null;
    if (!routingContext.request().method().name().equals(HttpMethod.GET.name())) {
      httpBody = routingContext.getBodyAsJson();      
    }
    if (httpBody != null) {
      result.put(MessageConstants.MSG_HTTP_BODY, httpBody);
    }
    result.put(MessageConstants.MSG_KEY_PREFS, (JsonObject)routingContext.get(MessageConstants.MSG_KEY_PREFS));
    result.put(MessageConstants.MSG_USER_ID, (String)routingContext.get(MessageConstants.MSG_USER_ID));
    return result;
  }

}
