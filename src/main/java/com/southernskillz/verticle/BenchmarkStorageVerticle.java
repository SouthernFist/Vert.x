package com.southernskillz.verticle;

import com.southernskillz.datastore.BenchmarkStore;
import com.southernskillz.handlers.BenchmarkRequestHandler;
import com.southernskillz.model.BenchmarkElement;
import io.vertx.core.AbstractVerticle;
import io.vertx.core.Future;
import io.vertx.core.Handler;
import io.vertx.core.eventbus.EventBus;
import io.vertx.core.eventbus.Message;
import io.vertx.core.json.Json;

import java.util.UUID;

/**
 * Created by michaelmorris on 2016-12-21.
 */
public class BenchmarkStorageVerticle extends AbstractVerticle{

    private BenchmarkRequestHandler br = new BenchmarkRequestHandler();
    private BenchmarkStore store = new BenchmarkStore();
    @Override
    public void start(Future<Void> fut){

        EventBus eb = vertx.eventBus();
        eb.consumer("/store", storeMessage());

        fut.complete();
    }

    public Handler<Message<Object>> storeMessage(){


        return handler -> {
            BenchmarkElement be = Json.decodeValue(handler.body().toString(), BenchmarkElement.class);
            String id = UUID.randomUUID().toString();
            be.setBenchId(id);
            store.put(id, be);
            handler.reply(Json.encodePrettily(be));
        };
    }
}
