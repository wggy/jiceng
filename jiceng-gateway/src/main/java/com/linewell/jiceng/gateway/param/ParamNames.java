package com.linewell.jiceng.gateway.param;

/***
 *  @author wping created on 2021-01-26 16:11 
 */
public class ParamNames {

    /** 分配给开发者的应用ID */
    public static String APP_KEY_NAME = "app_id";
    /** 接口名称 */
    public static String API_NAME = "method";
    /** 仅支持JSON */
    public static String FORMAT_NAME = "format";
    /** 请求使用的编码格式 */
    public static String CHARSET_NAME = "charset";
    /** 商户生成签名字符串所使用的签名算法类型，目前支持RSA2和RSA，推荐使用RSA2 */
    public static String SIGN_TYPE_NAME = "sign_type";
    /** 商户请求参数的签名串 */
    public static String SIGN_NAME = "sign";
    /** 发送请求的时间 */
    public static String TIMESTAMP_NAME = "timestamp";
    /** 调用的接口版本 */
    public static String VERSION_NAME = "version";
    /** OAuth 2.0授权token */
    public static String APP_AUTH_TOKEN_NAME = "app_auth_token";
    /** 请求参数的集合，最大长度不限，除公共参数外所有请求参数都必须放在这个参数中传递，具体参照各产品快速接入文档 */
    public static String BIZ_CONTENT_NAME = "biz_content";

    /** 时间戳格式 */
    public static String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /** 返回sign名称 */
    public static String RESPONSE_SIGN_NAME = "sign";

    public static String HEADER_VERSION_NAME = "sop-version";
}
