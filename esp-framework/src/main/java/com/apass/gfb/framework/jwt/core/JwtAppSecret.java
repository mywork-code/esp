package com.apass.gfb.framework.jwt.core;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * 
 * @description JWT APP KEY
 *
 * @author listening
 * @version $Id: JwtKey.java, v 0.1 2015年10月27日 下午10:20:58 listening Exp $
 */
@Component
public class JwtAppSecret {
    /**
     * audience
     */
    @Value("listening.jwt.audience?:listening-jwt-audience")
    private String audience   = "listening-jwt-audience";
    /**
     * issuer
     */
    @Value("listening.jwt.issuer?:listening-jwt-issuer")
    private String issuer     = "listening-jwt-issuer";
    /**
     * signing key
     */
    @Value("listening.jwt.signingkey?:listening-jwt-signingkey")
    private String signingKey = "listening-jwt-signingkey";

    public String getAudience() {
        return audience;
    }

    public void setAudience(String audience) {
        this.audience = audience;
    }

    public String getIssuer() {
        return issuer;
    }

    public void setIssuer(String issuer) {
        this.issuer = issuer;
    }

    public String getSigningKey() {
        return signingKey;
    }

    public void setSigningKey(String signingKey) {
        this.signingKey = signingKey;
    }

}
