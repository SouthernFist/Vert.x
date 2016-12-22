package com.southernskillz.handlers;

import com.southernskillz.datastore.PublicKeyStore;
import com.southernskillz.model.PublicCreds;
import io.jsonwebtoken.*;
import io.jsonwebtoken.impl.TextCodec;
import io.jsonwebtoken.impl.crypto.RsaProvider;
import io.jsonwebtoken.lang.Strings;
import io.vertx.core.logging.Logger;
import io.vertx.core.logging.LoggerFactory;

import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;
import java.util.UUID;

/**
 * Created by michaelmorris on 2016-12-18.
 */
public class CredentialsServices {

    private static final Logger log = LoggerFactory.getLogger(CredentialsServices.class);

    private KeyPair myKeyPair;
    private String kid;

    private PublicKeyStore publicKeys = new PublicKeyStore();

    private SigningKeyResolver signingKeyResolver = new SigningKeyResolverAdapter() {
        @Override
        public Key resolveSigningKey(JwsHeader header, Claims claims) {
            String kid = header.getKeyId();
            if (!Strings.hasText(kid)) {
                throw new JwtException("Missing required 'kid' header param in JWT with claims: " + claims);
            }
            Key key = publicKeys.get(kid);
            if (key == null) {
                throw new JwtException("No public key registered for kid: " + kid + ". JWT claims: " + claims);
            }
            return key;
        }
    };

    public PublicCreds refreshMyCreds() {
        myKeyPair = RsaProvider.generateKeyPair(1024);
        kid = UUID.randomUUID().toString();


        PublicCreds publicCreds = getMyPublicCreds();

        // this microservice will trust itself
        addPublicCreds(publicCreds);

        return publicCreds;
    }


    public SigningKeyResolver getSigningKeyResolver() {
        return signingKeyResolver;
    }

    public PublicCreds getPublicCreds(String kid) {
        return publicKeys.get(kid) != null ? createPublicCreds(kid, publicKeys.get(kid)) : null;
    }

    public PublicCreds getMyPublicCreds() {
        return createPublicCreds(this.kid, myKeyPair.getPublic());
    }

    private PublicCreds createPublicCreds(String kid, PublicKey key) {
        return new PublicCreds(kid, TextCodec.BASE64URL.encode(key.getEncoded()));
    }


    public void addPublicCreds(PublicCreds publicCreds) {
        byte[] encoded = TextCodec.BASE64URL.decode(publicCreds.getB64UrlPublicKey());

        PublicKey publicKey = null;
        try {
            publicKey = KeyFactory.getInstance("RSA").generatePublic(new X509EncodedKeySpec(encoded));
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.error("Unable to create public key: {}", e.getMessage(), e);
        }

        publicKeys.put(publicCreds.getKid(), publicKey);
    }


    // do not expose in controllers
    public PrivateKey getMyPrivateKey() {
        return myKeyPair.getPrivate();
    }

}
