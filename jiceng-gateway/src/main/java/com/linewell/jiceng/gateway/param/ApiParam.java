package com.linewell.jiceng.gateway.param;

import com.alibaba.fastjson.JSONObject;
import lombok.Data;

import java.util.Map;
import java.util.UUID;

/***
 *  @author wping created on 2021-01-26 15:45 
 */
@Data
public class ApiParam extends JSONObject {

    private String serviceId;
    private String ip;


    public ApiParam() {
    }

    public ApiParam(Map<String, Object> map) {
        super(map);
    }

    private String requestId = UUID.randomUUID().toString().replace("-", "");


    public static ApiParam build(Map<String, Object> map) {
        ApiParam apiParam = new ApiParam();
        for (Map.Entry<String, Object> entry : map.entrySet()) {
            apiParam.put(entry.getKey(), entry.getValue());
        }
        return apiParam;
    }

    private transient UploadContext uploadContext;
}
