package com.southernskillz.verticle;

import com.southernskillz.datastore.PublicKeyStore;
import com.southernskillz.handlers.CredentialsServices;
import com.southernskillz.model.PublicCreds;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

/**
 * Created by michaelmorris on 2016-12-18.
 */
public class ServicesVerticle extends AbstractVerticle {


    private static Logger logger = LoggerFactory.getLogger(ServicesVerticle.class);
    private CredentialsServices ch = new CredentialsServices();

    public void start(){

        EventBus eb = vertx.eventBus();
        eb.consumer("/refresh-keys", keyRefreshHandler());
        eb.consumer("/public-key/:id", getPublicKey());
        eb.consumer("/list/all", listAllKeys());
    }

    public Handler<Message<Object>> keyRefreshHandler(){

        ch.refreshMyCreds();
        return handler -> {
            ch.getMyPublicCreds();
            handler.reply(Json.encodePrettily(ch.getMyPublicCreds()));
        };
    }

    private Handler<Message<Object>> getPublicKey(){

        return handler ->{
            final String id = handler.body().toString();
            PublicCreds pc = ch.getPublicCreds(id);
            if (pc == null){
                handler.fail(404, "not.found");
            }else {
                handler.reply(pc.getB64UrlPublicKey());
            }

        };
    }

    private Handler<Message<Object>> listAllKeys(){

        PublicKeyStore pks = new PublicKeyStore();
        return handler ->{

            //Exceptions block. Need to trap it and send back a fail.  Not sure if this is the
            //proper way to do this.
            try{
                handler.reply(Json.encodePrettily(pks.listAll()));
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                handler.fail(500, "codec error");
            }

        };
    }
}
