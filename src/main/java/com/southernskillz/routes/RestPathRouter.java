package com.southernskillz.routes;

import com.southernskillz.handlers.RequestHandler;
import io.vertx.core.Vertx;
import io.vertx.ext.web.Router;

/**
 * Created by michaelmorris on 2016-12-21.
 */
public class RestPathRouter extends PathRouter {


    public RestPathRouter(Vertx vertx) {
        super(vertx);
    }

    @Override
    public Router buildRoutes(Vertx vertx) {

        RequestHandler handler = new RequestHandler();

        Router router = getRouter();
        router.post("/echo").handler(handler::echo);
        router.get("/refresh-keys").handler(handler::refreshKeys);
        router.get("/public-key/:id").handler(handler::retrievePublicKey);
        router.get("/list/all").handler(handler::listAllKeys);

        return router;
    }


}
