package com.linewell.jiceng.core.bean;

import com.linewell.jiceng.core.route.RouteDefinition;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
import java.util.Objects;

/***
 *  @author wping created on 2021-01-29 13:55
 */
@Data
public class ServiceApiInfo {

    private String serviceId;
    private List<ApiMeta> apis;
    private List<RouteDefinition> routeDefinitionList;

    @Getter
    @Setter
    public static class ApiMeta {
        /**
         * 接口名
         */
        private String name;
        /**
         * 请求path
         */
        private String path;
        /**
         * 是否需要token
         */
        private Boolean needToken;

        public ApiMeta() {
        }

        public ApiMeta(String name, String path, Boolean needToken) {
            this.name = name;
            this.path = path;
            this.needToken = needToken;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            ApiMeta apiMeta = (ApiMeta) o;
            return name.equals(apiMeta.name) &&
                    Objects.equals(path, apiMeta.path);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, path);
        }
    }
}
