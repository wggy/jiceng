package com.linewell.jiceng.gateway.route;

import lombok.Data;

/***
 *  @author wping created on 2021-01-26 16:10 
 */
@Data
public class ForwardInfo {

    private TargetRoute targetRoute;

    public static ForwardInfo getErrorForwardInfo() {
        return ErrorForwardInfo.errorForwardInfo;
    }

    public ForwardInfo(TargetRoute targetRoute) {
        this.targetRoute = targetRoute;
    }

    public String getPath() {
        return targetRoute.getRouteDefinition().getPath();
    }


    static class ErrorForwardInfo extends ForwardInfo {

        private static final String VALIDATE_ERROR_PATH = "/sop/validateError";

        public static ErrorForwardInfo errorForwardInfo = new ErrorForwardInfo();

        public ErrorForwardInfo() {
            this(null);
        }

        public ErrorForwardInfo(TargetRoute targetRoute) {
            super(targetRoute);
        }

        @Override
        public String getPath() {
            return VALIDATE_ERROR_PATH;
        }

    }
}
