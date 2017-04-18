package com.apass.gfb.framework.jwt.common;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpMethod;

/**
 * 
 * @description Cors Utils
 *
 * @author lixining
 * @version $Id: HttpCorsUtils.java, v 0.1 2016年1月11日 上午11:06:25 lixining Exp $
 */
public class HttpCorsUtils {

    /**
     * Returns {@code true} if the request is a valid CORS one.
     */
    public static boolean isCorsRequest(HttpServletRequest request) {
        return (request.getHeader(CorsEnum.ORIGIN.getCode()) != null);
    }

    /**
     * Returns {@code true} if the request is a valid CORS pre-flight one.
     */
    public static boolean isPreFlightRequest(HttpServletRequest request) {
        String reqMethod = request.getMethod();
        String optionsMethod = HttpMethod.OPTIONS.name();
        boolean isMethodOptions = StringUtils.equalsIgnoreCase(optionsMethod, reqMethod);
        boolean isCorsRequest = isCorsRequest(request);
        String requestheader = request.getHeader(CorsEnum.ACCESS_CONTROL_REQUEST_METHOD.getCode());
        return (isCorsRequest && isMethodOptions && requestheader != null);
    }

    /**
     * Allow Cors Once
     * 
     * @param request
     * @param response
     */
    public static final void allowCorsOnce(HttpServletRequest request, HttpServletResponse response) {
        String origin = request.getHeader(CorsEnum.ORIGIN.getCode());
        response.setHeader(CorsEnum.ACCESS_CONTROL_ALLOW_ORIGIN.getCode(), origin);
        response.setHeader(CorsEnum.ACCESS_CONTROL_ALLOW_CREDENTIALS.getCode(), "true");
    }

    public enum CorsEnum {
        // -------------------------------------------------- CORS Response Headers
        /**
         * The Access-Control-Allow-Origin header indicates whether a resource can
         * be shared based by returning the value of the Origin request header in
         * the response.
         */
        ACCESS_CONTROL_ALLOW_ORIGIN("Access-Control-Allow-Origin"),
        /**
         * The Access-Control-Allow-Credentials header indicates whether the
         * response to request can be exposed when the omit credentials flag is
         * unset. When part of the response to a preflight request it indicates that
         * the actual request can include user credentials.
         */
        ACCESS_CONTROL_ALLOW_CREDENTIALS("Access-Control-Allow-Credentials"),
        /**
         * The Access-Control-Expose-Headers header indicates which headers are safe
         * to expose to the API of a CORS API specification
         */
        ACCESS_CONTROL_EXPOSE_HEADERS("Access-Control-Expose-Headers"),
        /**
         * The Access-Control-Max-Age header indicates how long the results of a
         * preflight request can be cached in a preflight result cache.
         */
        ACCESS_CONTROL_MAX_AGE("Access-Control-Max-Age"),
        /**
         * The Access-Control-Allow-Methods header indicates, as part of the
         * response to a preflight request, which methods can be used during the
         * actual request.
         */
        ACCESS_CONTROL_ALLOW_METHODS("Access-Control-Allow-Methods"),
        /**
         * The Access-Control-Allow-Headers header indicates, as part of the
         * response to a preflight request, which header field names can be used
         * during the actual request.
         */
        ACCESS_CONTROL_ALLOW_HEADERS("Access-Control-Allow-Headers"),
        // -------------------------------------------------- CORS Request Headers
        /**
         * The Origin header indicates where the cross-origin request or preflight
         * request originates from.
         */
        ORIGIN("Origin"),
        /**
         * The Access-Control-Request-Method header indicates which method will be
         * used in the actual request as part of the preflight request.
         */
        ACCESS_CONTROL_REQUEST_METHOD("Access-Control-Request-Method"),
        /**
         * The Access-Control-Request-Headers header indicates which headers will be
         * used in the actual request as part of the preflight request.
         */
        ACCESS_CONTROL_REQUEST_HEADERS("Access-Control-Request-Headers"),
        /**
         * Header Validate Field For JSON Token
         */
        X_AUTH_TOKEN("X-Auth-Token");

        private String code;

        private CorsEnum(String code) {
            this.code = code;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
