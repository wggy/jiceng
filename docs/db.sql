CREATE TABLE `jc_service_route`
(
    `id`             int(11) NOT NULL AUTO_INCREMENT COMMENT '主键ID',
    `service_id`     varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '服务名称',
    `interface_name` varchar(64) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '接口名称',
    `predicates`     varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '路由断言（SpringCloudGateway专用）',
    `filters`        varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT '路由过滤器（SpringCloudGateway专用）',
    `uri`            varchar(128) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL COMMENT '路由规则转发的目标uri',
    `path`           varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NULL DEFAULT NULL COMMENT 'uri后面跟的path',
    `status`         bit(1) NULL DEFAULT b'1' COMMENT '状态，0禁用，1启用',
    `need_token`     bit(1) NULL DEFAULT b'1' COMMENT '是否需要token，0不需要，1需要',
    `create_time`    datetime NULL DEFAULT NULL COMMENT '创建时间',
    `update_time`    datetime NULL DEFAULT NULL COMMENT '修改时间',
    PRIMARY KEY (`id`) USING BTREE
) ENGINE = InnoDB AUTO_INCREMENT = 1 CHARACTER SET = utf8mb4 COLLATE = utf8mb4_bin COMMENT = '接口路由表' ROW_FORMAT = Dynamic;