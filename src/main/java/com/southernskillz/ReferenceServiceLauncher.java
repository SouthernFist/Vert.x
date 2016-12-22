package com.southernskillz;

import io.vertx.core.AbstractVerticle;
import io.vertx.core.DeploymentOptions;
import io.vertx.core.Vertx;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * Created by michaelmorris on 2016-12-18.
 */
public class ReferenceServiceLauncher extends AbstractVerticle{

    private static final Logger logger = LoggerFactory.getLogger(ReferenceServiceLauncher.class);

    public void start(){

        DeploymentOptions options = new DeploymentOptions();
        logger.info("Number of processors: " + Runtime.getRuntime().availableProcessors());
        options.setInstances(Runtime.getRuntime().availableProcessors()); //Deploy one verticle per available processor/core.

        Vertx vertx = Vertx.vertx();
        vertx.deployVerticle("com.southernskillz.verticle.BenchmarkRestVerticle", options, res -> {
            if (res.succeeded()) {
                logger.info("RestVerticle Deployed. Deploy id is: " + res.result());
            } else {
                logger.info("Deployment failed!");
                logger.info(res.cause());
            }
        });

          //This section requires Hazelcast to be deployed.  Omitting this piece for now
//        options = new DeploymentOptions();
//        options.setWorker(true);
//        options.setWorkerPoolSize(50);
//        vertx.deployVerticle("com.rubylife.verticle.BenchmarkStorageVerticle", options, res -> {
//            if (res.succeeded()) {
//                logger.info("RestVerticle Vertx Deployed. Deploy id is: " + res.result());
//            } else {
//                logger.info("Deployment failed!");
//                logger.info(res.cause());
//            }
//        });
    }



//    public static void main(String... args){
//
//        DeploymentOptions options = new DeploymentOptions();
//        logger.info("Number of processors: " + Runtime.getRuntime().availableProcessors());
//        options.setInstances(Runtime.getRuntime().availableProcessors()); //Deploy one verticle per available processor/core.
//
//        Vertx vertx = Vertx.vertx();
//        vertx.deployVerticle("com.rubylife.verticle.BenchmarkRestVerticle", options, res -> {
//            if (res.succeeded()) {
//                logger.info("RestVerticle Deployed. Deploy id is: " + res.result());
//            } else {
//                logger.info("Deployment failed!");
//                logger.info(res.cause());
//            }
//        });

        //This section requires Hazelcast to be deployed.  Omitting this piece for now
//        options = new DeploymentOptions();
//        options.setWorker(true);
//        vertx.deployVerticle("com.rubylife.verticle.BenchmarkStorageVerticle", options, res -> {
//            if (res.succeeded()) {
//                logger.info("RestVerticle Vertx Deployed. Deploy id is: " + res.result());
//            } else {
//                logger.info("Deployment failed!");
//                logger.info(res.cause());
//            }
//        });
//    }
}
