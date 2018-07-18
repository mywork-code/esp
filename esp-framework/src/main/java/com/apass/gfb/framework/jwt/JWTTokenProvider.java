package com.apass.gfb.framework.jwt;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.Date;

/**
 * JWTTokenProvider.java
 */
 @Component
public class JWTTokenProvider {
    private static final String AUTHORITIES_KEY = "auth";
    /**
     * Sign key
     */
    private String secretKey;
    /**
     * 有效时长
     */
    private long tokenValidityInSeconds;
    /**
     * 记住登陆-有效时长
     */
    private long tokenValidityInSecondsForRememberMe;


    /**
     * 构造初始参数
     */
    @PostConstruct
    public void init() {
        this.secretKey = "wodliwu";
        this.tokenValidityInSeconds = 1000 * 60 * 30;
        this.tokenValidityInSecondsForRememberMe = 1000 * 7 * 24 * 60 * 60;
    }

    /**
     * 创建登陆Token
     *
     * @param subject     主题
     * @param jsonContent 加密内容
     * @param rememberMe  是否记住登陆
     * @return String
     */
    public String createToken(String subject, String jsonContent, Boolean rememberMe) {
        Date validity = new Date((new Date()).getTime() + (rememberMe ? this.tokenValidityInSecondsForRememberMe : this.tokenValidityInSeconds));
        return Jwts.builder().setSubject(subject).claim(AUTHORITIES_KEY, jsonContent).signWith(SignatureAlgorithm.HS512, secretKey).setExpiration(validity).compact();
    }

    /**
     * Parse Token
     *
     * @param claims token claims
     * @return String
     */
    public String getTokenBody(Claims claims) {
        return claims.get(AUTHORITIES_KEY).toString();
    }

    /**
     * 解析Token的Claims
     *
     * @param token token
     * @return Claims
     */
    public Claims parseTokenClaims(String token) {
        return Jwts.parser().setSigningKey(secretKey).parseClaimsJws(token).getBody();
    }

    /**
     * 解析Token的Body
     *
     * @param token token
     * @return String
     */
    public String getTokenBody(String token) {
        return parseTokenClaims(token).get(AUTHORITIES_KEY).toString();
    }

    /**
     * Parse Validate Token
     *
     * @param authToken 认证Token
     * @return Boolean
     */
    public boolean validateToken(String authToken) {
        try {
            Jwts.parser().setSigningKey(secretKey).parseClaimsJws(authToken);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String createToken(String jsonContent, Boolean rememberMe) {
        return createToken("jwt_without_spring_security", jsonContent, rememberMe);
    }
}
