package com.southernskillz.routes;

import io.vertx.core.Handler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.RoutingContext;
import io.vertx.ext.web.handler.BodyHandler;

/**
 * Created by michaelmorris on 2016-12-18.
 */
public abstract class PathRouter {


    private Vertx vertx;
    private Router router;
    private Handler<RoutingContext> handler;

    public PathRouter(Vertx vertx){

        this.vertx = vertx;
        router = Router.router(vertx);
        router.route().handler(BodyHandler.create());
        router.route().consumes("application/json"); //Routes consume json
        router.route().produces("application/json"); //Routes produce json
    }

    public void setHandler(Handler<RoutingContext> handler){

        this.handler = handler;
    }

    public Router getRouter() {
        return router;
    }

    public void setRouter(Router router) {
        this.router = router;
    }

    public Handler<RoutingContext> getHandler() {
        return handler;
    }

    public abstract Router buildRoutes(Vertx vertx);

}
