package com.linewell.jiceng.gateway.route;

import com.linewell.jiceng.gateway.JiCengConstants;

import java.util.Map;

/***
 *  @author wping created on 2021-01-25 15:48 
 */
public interface RegistryMetadata {

    default String getContextPath(Map<String, String> metadata) {
        return metadata.getOrDefault(JiCengConstants.METADATA_SERVER_CONTEXT_PATH
                , metadata.getOrDefault(JiCengConstants.METADATA_SERVER_CONTEXT_PATH_COMPATIBILITY, ""));
    }
}
