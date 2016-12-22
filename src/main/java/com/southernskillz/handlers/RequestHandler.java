package com.southernskillz.handlers;

import io.vertx.core.eventbus.Message;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.RoutingContext;

/**
 * Created by michaelmorris on 2016-12-18.
 */
public class RequestHandler {

    private static final Logger logger = LoggerFactory.getLogger(RequestHandler.class);

    public void echo(RoutingContext context) {

        String msg = context.getBodyAsString();
        context.response().end(msg); //Send what was received back to the caller.
    }

    public void refreshKeys(RoutingContext context){

        context.vertx().eventBus().send("/refresh-keys", "/refresh-keys",
                responseHandler -> {
                    if (responseHandler.failed()){
                        context.fail(500);
                    }else {
                        final Message<Object> result = responseHandler.result();
                        context.response().end(result.body().toString());
                    }
                });
    }

    public void retrievePublicKey(RoutingContext context){

        String id = context.request().getParam("id");
        context.vertx().eventBus().send("/public-key/:id", id,
                responseHandler -> {
                    if (responseHandler.failed() &&
                            "not.found".equalsIgnoreCase(responseHandler.cause().getMessage()) ){ //This is probably not the way to do this.  Still investigating.
                        context.response().setStatusCode(404).end();
                    }else {
                        final Message<Object> result = responseHandler.result();
                        context.response().end(result.body().toString());
                    }
                });
    }

    public void listAllKeys(RoutingContext context){

        context.vertx().eventBus().send("/list/all", "list/all",
                responseHandler ->{
                    if (responseHandler.failed()){
                        context.fail(500);
                    }else {
                        Message<Object> result = responseHandler.result();
                        context.response().end(result.body().toString());
                    }
                });
    }


}
