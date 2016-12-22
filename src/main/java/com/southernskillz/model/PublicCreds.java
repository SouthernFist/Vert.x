package com.southernskillz.model;

/**
 * Created by michaelmorris on 2016-12-18.
 */
public class PublicCreds {

    private final String kid;
    private final String b64UrlPublicKey;


    public PublicCreds(String kid,  String b64UrlPublicKey) {
        this.kid = kid;
        this.b64UrlPublicKey = b64UrlPublicKey;
    }

    public String getKid() {
        return kid;
    }

    public String getB64UrlPublicKey() {
        return b64UrlPublicKey;
    }
}
