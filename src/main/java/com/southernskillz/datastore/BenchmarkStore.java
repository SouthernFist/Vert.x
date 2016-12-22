package com.southernskillz.datastore;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import com.southernskillz.model.BenchmarkElement;

import java.util.Map;

/**
 * Created by michaelmorris on 2016-12-21.
 */
public class BenchmarkStore {

    private static final String KEY_STORE_MAP = "benchmarkstore";
    private HazelcastInstance instance;

    public BenchmarkStore(){

        instance = HazelcastClient.newHazelcastClient();
    }

    public Map<String, BenchmarkElement> getKeyMap(){

        return instance.getMap(KEY_STORE_MAP);
    }

    public BenchmarkElement get(String keyId){

        return getKeyMap().get(keyId);
    }

    public void put(String keyId, BenchmarkElement be){

        getKeyMap().put(keyId, be);
    }
}
