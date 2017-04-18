package com.apass.gfb.framework.jwt.core;

import java.security.SignatureException;
import java.util.Calendar;
import java.util.List;

import net.oauth.jsontoken.JsonToken;
import net.oauth.jsontoken.JsonTokenParser;
import net.oauth.jsontoken.crypto.HmacSHA256Signer;
import net.oauth.jsontoken.crypto.HmacSHA256Verifier;
import net.oauth.jsontoken.crypto.SignatureAlgorithm;
import net.oauth.jsontoken.crypto.Verifier;
import net.oauth.jsontoken.discovery.VerifierProvider;
import net.oauth.jsontoken.discovery.VerifierProviders;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.apass.gfb.framework.jwt.common.EncodeUtils;
import com.apass.gfb.framework.jwt.domains.TokenInfo;
import com.google.common.collect.Lists;
import com.google.gson.JsonObject;


/**
 * 
 * @description Json Token Helper
 *
 * @author lixining
 * @version $Id: JsonTokenHelper.java, v 0.1 2015年10月28日 上午11:32:49 lixining Exp $
 */
@Component
public class JsonTokenHelper {
	
    /**
     * JSON TOKEN APP
     */
    @Autowired
    private  JwtAppSecret jwtApp;

    /**
     * Creates a json web token which is a digitally signed token that contains a payload
     * (e.g. userId to identify the user). The signing key is secret. That ensures that the
     * token is authentic and has not been modified.
     * 
     * Using a jwt eliminates the need to store authentication session information in a database.
     * 
     * @param userId
     * @return String
     */
    public   String createJsonWebToken(String userId, String mobile, Long expires) {
        try {
            String audience = jwtApp.getAudience();
            String issuer = jwtApp.getIssuer();
            String signKey = jwtApp.getSigningKey();

            Calendar cal = Calendar.getInstance();
            byte[] signingKeyByte = EncodeUtils.getBytes(signKey);
            HmacSHA256Signer signer = new HmacSHA256Signer(issuer, null, signingKeyByte);

            // Configure JSON token
            JsonToken token = new net.oauth.jsontoken.JsonToken(signer);
            token.setAudience(audience);
            token.setIssuedAt(new org.joda.time.Instant(cal.getTimeInMillis()));
            token.setExpiration(new org.joda.time.Instant(cal.getTimeInMillis() + expires * 1000L));

            // Configure request object, which provides information of the item
            JsonObject request = new JsonObject();
            request.addProperty("userId", userId);
            request.addProperty("mobile", mobile);

            JsonObject payload = token.getPayloadAsJsonObject();
            payload.add("info", request);

            return token.serializeAndSign();
        } catch (Exception e) {
            throw new RuntimeException("create json token fail", e);
        }
    }

    /**
     * Json Web Token Verify
     * 
     * @param token
     * @return TokenInfo
     */
    public  TokenInfo verifyToken(String token) {
        try {
            String issuer = jwtApp.getIssuer();
            String signKey = jwtApp.getSigningKey();

            byte[] signingKeyByte = EncodeUtils.getBytes(signKey);
            final Verifier hmacVerifier = new HmacSHA256Verifier(signingKeyByte);
            VerifierProvider hmacLocator = new VerifierProvider() {
                @Override
                public List<Verifier> findVerifier(String id, String key) {
                    return Lists.newArrayList(hmacVerifier);
                }
            };

            VerifierProviders locators = new VerifierProviders();
            locators.setVerifierProvider(SignatureAlgorithm.HS256, hmacLocator);
            net.oauth.jsontoken.Checker checker = new net.oauth.jsontoken.Checker() {
                @Override
                public void check(JsonObject payload) throws SignatureException {
                    // don't throw - allow anything
                }
            };

            // Ignore Audience does not mean that the Signature is ignored
            JsonTokenParser parser = new JsonTokenParser(locators, checker);
            JsonToken jt = parser.verifyAndDeserialize(token);

            JsonObject payload = jt.getPayloadAsJsonObject();
            String issuerResult = payload.getAsJsonPrimitive("iss").getAsString();
            String userIdString = payload.getAsJsonObject("info").getAsJsonPrimitive("userId").getAsString();
            String mobileString = payload.getAsJsonObject("info").getAsJsonPrimitive("mobile").getAsString();
            TokenInfo resultToken = null;
            if (!issuer.equals(issuerResult) || StringUtils.isBlank(userIdString) || StringUtils.isBlank(mobileString)) {
                return null;
            }
            resultToken = new TokenInfo();
            resultToken.setUserId(userIdString);
            resultToken.setMobile(mobileString);
            resultToken.setIssued(payload.getAsJsonPrimitive("iat").getAsLong());
            resultToken.setExpires(payload.getAsJsonPrimitive("exp").getAsLong());
            return resultToken;
        } catch (Exception e) {
            throw new RuntimeException("json token verify fail", e);
        }
    }
    
}
