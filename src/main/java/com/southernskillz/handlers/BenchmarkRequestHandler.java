package com.southernskillz.handlers;

import io.vertx.core.eventbus.Message;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by michaelmorris on 2016-12-21.
 */
public class BenchmarkRequestHandler {

    public void storeMessage(RoutingContext context){

        context.vertx().eventBus().send("/store", context.getBody(),
                responseHandler -> {
                    if (responseHandler.failed()){
                        context.fail(500);
                    }else {
                        final Message<Object> result = responseHandler.result();
                        context.response().end(result.body().toString());
                    }
                });

    }

    public void echoMessage(RoutingContext context){

        context.response().end(context.getBody().toString());
    }
}
