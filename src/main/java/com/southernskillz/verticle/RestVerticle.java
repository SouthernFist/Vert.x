package com.southernskillz.verticle;


import com.southernskillz.routes.RestPathRouter;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;
import io.vertx.ext.web.Router;
import io.vertx.ext.web.handler.ErrorHandler;
import io.vertx.ext.web.handler.StaticHandler;

/**
 * Created by michaelmorris on 2016-12-18.
 */
public class RestVerticle extends AbstractVerticle {

    private static final Logger logger = LoggerFactory.getLogger(RestVerticle.class);

    @Override
    public void start(Future<Void> fut){

        Router router = Router.router(vertx);
        router.mountSubRouter("/api", new RestPathRouter(vertx).buildRoutes(vertx));
        router.route().failureHandler(errorHandler());
        router.route().handler(staticHandler());

        vertx.createHttpServer().requestHandler(router::accept).listen(
                config().getInteger("http.port", 8080),
                result -> {

                    if (result.succeeded()){
                        fut.complete();
                    }else {
                        fut.fail(result.cause());
                    }
                }
        );
    }

    public ErrorHandler errorHandler(){

        return ErrorHandler.create(true);
    }

    public StaticHandler staticHandler(){

        return StaticHandler.create().setCachingEnabled(false);
    }
}
