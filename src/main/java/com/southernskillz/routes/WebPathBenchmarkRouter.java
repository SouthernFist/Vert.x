package com.southernskillz.routes;

import com.southernskillz.handlers.BenchmarkRequestHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

/**
 * Created by michaelmorris on 2016-12-21.
 */
public class WebPathBenchmarkRouter extends PathRouter {


    public WebPathBenchmarkRouter(Vertx vertx) {
        super(vertx);
    }

    @Override
    public Router buildRoutes(Vertx vertx) {
        //Route Definitions
        BenchmarkRequestHandler handler = new BenchmarkRequestHandler();
        Router router = getRouter();
        router.post("/store").handler(handler::storeMessage);
        router.post("/echo").handler(handler::echoMessage);
        return router;
    }
}
