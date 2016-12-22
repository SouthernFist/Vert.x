package com.southernskillz.datastore;

import com.hazelcast.client.HazelcastClient;
import com.hazelcast.core.HazelcastInstance;
import io.jsonwebtoken.impl.TextCodec;
import java.security.PublicKey;
import java.util.Collection;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Created by michaelmorris on 2016-12-18.
 */
public class PublicKeyStore {

    private static final String KEY_STORE_MAP = "keystore";
    private HazelcastInstance instance;

    public PublicKeyStore(){

        instance = HazelcastClient.newHazelcastClient();
    }

    public Map<String, PublicKey> getKeyMap(){

        return instance.getMap(KEY_STORE_MAP);
    }

    public PublicKey get(String keyId){

        return getKeyMap().get(keyId);
    }

    public void put(String keyId, PublicKey pk){

        getKeyMap().put(keyId, pk);
    }

    public Collection<String> listAll(){

        return getKeyMap().values().stream()
                .map(item -> TextCodec.BASE64URL.encode(item.getEncoded()))
                .collect(Collectors.toList());
    }

}
