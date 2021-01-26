package com.linewell.jiceng.gateway.util;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;

/***
 *  @author wping created on 2021-01-25 11:38 
 */
public interface JiCengConstants {

    String UTF8 = "UTF-8";
    Charset CHARSET_UTF8 = StandardCharsets.UTF_8;
    String CACHE_API_PARAM = "cacheApiParam";
    String CACHE_ROUTE_INFO = "cacheRouteInfo";

    String CACHE_UPLOAD_REQUEST = "cacheUploadRequest";
    String METADATA_KEY_TIME_STARTUP = "server.startup-time";

    String METADATA_SERVER_CONTEXT_PATH = "server.servlet.context-path";

    String METADATA_SERVER_CONTEXT_PATH_COMPATIBILITY = "context-path";


    String CACHE_ROUTE_INTERCEPTOR_CONTEXT = "cacheRouteInterceptorContext";
    String TARGET_SERVICE = "sop-target-service";
    String RESTFUL_REQUEST = "sop-restful-request";

}
