package org.gooru.nucleus.gateway.routes;

import io.vertx.core.Vertx;
import io.vertx.core.eventbus.DeliveryOptions;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.json.JsonObject;
import io.vertx.ext.web.Router;
import org.gooru.nucleus.gateway.constants.ConfigConstants;
import org.gooru.nucleus.gateway.constants.MessageConstants;
import org.gooru.nucleus.gateway.constants.MessagebusEndpoints;
import org.gooru.nucleus.gateway.constants.RouteConstants;
import org.gooru.nucleus.gateway.routes.utils.RouteRequestUtility;
import org.gooru.nucleus.gateway.routes.utils.RouteResponseUtility;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

class RouteLookupConfigurator implements RouteConfigurator {

  private static final Logger LOG = LoggerFactory.getLogger("org.gooru.nucleus.gateway.bootstrap.ServerVerticle");

  @Override
  public void configureRoutes(Vertx vertx, Router router, JsonObject config) {

    final EventBus eb = vertx.eventBus();

    final long mbusTimeout = config.getLong(ConfigConstants.MBUS_TIMEOUT, 30L);

    router.get(RouteConstants.EP_EDUCATIONAL_USE_LIST).handler(routingContext -> {
      DeliveryOptions options =
        new DeliveryOptions().setSendTimeout(mbusTimeout * 1000).addHeader(MessageConstants.MSG_HEADER_OP, MessageConstants.MSG_OP_LKUP_EDU_USE);
      eb.send(MessagebusEndpoints.MBEP_LOOKUP, new RouteRequestUtility().getBodyForMessage(routingContext), options,
        reply -> new RouteResponseUtility().responseHandler(routingContext, reply, LOG));
    });

    router.get(RouteConstants.EP_ACCESS_HAZARDS_LIST).handler(routingContext -> {
      DeliveryOptions options = new DeliveryOptions().setSendTimeout(mbusTimeout * 1000)
                                                     .addHeader(MessageConstants.MSG_HEADER_OP, MessageConstants.MSG_OP_LKUP_ACCESS_HAZARDS);
      eb.send(MessagebusEndpoints.MBEP_LOOKUP, new RouteRequestUtility().getBodyForMessage(routingContext), options,
        reply -> new RouteResponseUtility().responseHandler(routingContext, reply, LOG));
    });

    router.get(RouteConstants.EP_READING_LEVELS_LIST).handler(routingContext -> {
      DeliveryOptions options =
        new DeliveryOptions().setSendTimeout(mbusTimeout * 1000).addHeader(MessageConstants.MSG_HEADER_OP, MessageConstants.MSG_OP_LKUP_READ_LEVEL);
      eb.send(MessagebusEndpoints.MBEP_LOOKUP, new RouteRequestUtility().getBodyForMessage(routingContext), options,
        reply -> new RouteResponseUtility().responseHandler(routingContext, reply, LOG));
    });

    router.get(RouteConstants.EP_AD_STATUS_LIST).handler(routingContext -> {
      DeliveryOptions options =
        new DeliveryOptions().setSendTimeout(mbusTimeout * 1000).addHeader(MessageConstants.MSG_HEADER_OP, MessageConstants.MSG_OP_LKUP_AD_STATUS);
      eb.send(MessagebusEndpoints.MBEP_LOOKUP, new RouteRequestUtility().getBodyForMessage(routingContext), options,
        reply -> new RouteResponseUtility().responseHandler(routingContext, reply, LOG));
    });

    router.get(RouteConstants.EP_MEDIA_FEATURES_LIST).handler(routingContext -> {
      DeliveryOptions options = new DeliveryOptions().setSendTimeout(mbusTimeout * 1000)
                                                     .addHeader(MessageConstants.MSG_HEADER_OP, MessageConstants.MSG_OP_LKUP_MEDIA_FEATURES);
      eb.send(MessagebusEndpoints.MBEP_LOOKUP, new RouteRequestUtility().getBodyForMessage(routingContext), options,
        reply -> new RouteResponseUtility().responseHandler(routingContext, reply, LOG));
    });

    router.get(RouteConstants.EP_21_CENTURY_SKILLS_LIST).handler(routingContext -> {
      DeliveryOptions options = new DeliveryOptions().setSendTimeout(mbusTimeout * 1000)
                                                     .addHeader(MessageConstants.MSG_HEADER_OP, MessageConstants.MSG_OP_LKUP_21_CEN_SKILLS);
      eb.send(MessagebusEndpoints.MBEP_LOOKUP, new RouteRequestUtility().getBodyForMessage(routingContext), options,
        reply -> new RouteResponseUtility().responseHandler(routingContext, reply, LOG));
    });

    router.get(RouteConstants.EP_GRADES_LIST).handler(routingContext -> {
      DeliveryOptions options =
        new DeliveryOptions().setSendTimeout(mbusTimeout * 1000).addHeader(MessageConstants.MSG_HEADER_OP, MessageConstants.MSG_OP_LKUP_GRADE);
      eb.send(MessagebusEndpoints.MBEP_LOOKUP, new RouteRequestUtility().getBodyForMessage(routingContext), options,
        reply -> new RouteResponseUtility().responseHandler(routingContext, reply, LOG));
    });


    router.get(RouteConstants.EP_DOK_LIST).handler(routingContext -> {
      DeliveryOptions options =
        new DeliveryOptions().setSendTimeout(mbusTimeout * 1000).addHeader(MessageConstants.MSG_HEADER_OP, MessageConstants.MSG_OP_LKUP_DOK);
      eb.send(MessagebusEndpoints.MBEP_LOOKUP, new RouteRequestUtility().getBodyForMessage(routingContext), options,
        reply -> new RouteResponseUtility().responseHandler(routingContext, reply, LOG));
    });


    router.get(RouteConstants.EP_MOMENTS_LIST).handler(routingContext -> {
      DeliveryOptions options =
        new DeliveryOptions().setSendTimeout(mbusTimeout * 1000).addHeader(MessageConstants.MSG_HEADER_OP, MessageConstants.MSG_OP_LKUP_MOMENTS);
      eb.send(MessagebusEndpoints.MBEP_LOOKUP, new RouteRequestUtility().getBodyForMessage(routingContext), options,
        reply -> new RouteResponseUtility().responseHandler(routingContext, reply, LOG));
    });

    router.get(RouteConstants.EP_AUDIENCE_LIST).handler(routingContext -> {
      DeliveryOptions options =
        new DeliveryOptions().setSendTimeout(mbusTimeout * 1000).addHeader(MessageConstants.MSG_HEADER_OP, MessageConstants.MSG_OP_LKUP_AUDIENCE);
      eb.send(MessagebusEndpoints.MBEP_LOOKUP, new RouteRequestUtility().getBodyForMessage(routingContext), options,
        reply -> new RouteResponseUtility().responseHandler(routingContext, reply, LOG));
    });


    router.get(RouteConstants.EP_STATES_LIST).handler(routingContext -> {
      String stateKeyword = routingContext.request().query();
      String countryId = routingContext.request().getParam(RouteConstants.ID_COUNTRY);
      DeliveryOptions options =
        new DeliveryOptions().setSendTimeout(mbusTimeout * 1000).addHeader(MessageConstants.MSG_HEADER_OP, MessageConstants.MSG_OP_LKUP_STATES)
                             .addHeader(RouteConstants.ID_COUNTRY, countryId).addHeader(RouteConstants.ID_LKUP_QUERY, stateKeyword);
      eb.send(MessagebusEndpoints.MBEP_LOOKUP, new RouteRequestUtility().getBodyForMessage(routingContext), options,
        reply -> new RouteResponseUtility().responseHandler(routingContext, reply, LOG));
    });

    router.get(RouteConstants.EP_COUNTRIES_LIST).handler(routingContext -> {
      String countryKeyword = routingContext.request().query();
      DeliveryOptions options =
        new DeliveryOptions().setSendTimeout(mbusTimeout * 1000).addHeader(MessageConstants.MSG_HEADER_OP, MessageConstants.MSG_OP_LKUP_COUNTRIES)
                             .addHeader(RouteConstants.ID_LKUP_QUERY, countryKeyword);
      eb.send(MessagebusEndpoints.MBEP_LOOKUP, new RouteRequestUtility().getBodyForMessage(routingContext), options,
        reply -> new RouteResponseUtility().responseHandler(routingContext, reply, LOG));
    });

    router.get(RouteConstants.EP_SCHOOLDISTRICTS_LIST).handler(routingContext -> {
      String schoolDistrictKeyword = routingContext.request().query();
      DeliveryOptions options = new DeliveryOptions().setSendTimeout(mbusTimeout * 1000)
                                                     .addHeader(MessageConstants.MSG_HEADER_OP, MessageConstants.MSG_OP_LKUP_SCHOOLDISTRICTS)
                                                     .addHeader(RouteConstants.ID_LKUP_QUERY, schoolDistrictKeyword);
      eb.send(MessagebusEndpoints.MBEP_LOOKUP, new RouteRequestUtility().getBodyForMessage(routingContext), options,
        reply -> new RouteResponseUtility().responseHandler(routingContext, reply, LOG));
    });

    router.get(RouteConstants.EP_SCHOOLS_LIST).handler(routingContext -> {
      String schoolKeyword = routingContext.request().query();
      DeliveryOptions options =
        new DeliveryOptions().setSendTimeout(mbusTimeout * 1000).addHeader(MessageConstants.MSG_HEADER_OP, MessageConstants.MSG_OP_LKUP_SCHOOLS)
                             .addHeader(RouteConstants.ID_LKUP_QUERY, schoolKeyword);
      eb.send(MessagebusEndpoints.MBEP_LOOKUP, new RouteRequestUtility().getBodyForMessage(routingContext), options,
        reply -> new RouteResponseUtility().responseHandler(routingContext, reply, LOG));
    });

  }

}
