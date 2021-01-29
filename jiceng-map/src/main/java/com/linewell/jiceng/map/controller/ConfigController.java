package com.linewell.jiceng.map.controller;

import com.alibaba.nacos.api.config.annotation.NacosValue;
import com.linewell.jiceng.map.entity.UserInfoEntity;
import com.linewell.jiceng.map.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/config")
public class ConfigController {


    @Value(value = "${useLocalCache: false}")
    private Boolean useLocalCache;

    @Autowired
    private UserInfoService userInfoService;

    @GetMapping("get")
    public List<UserInfoEntity> get() {
        return userInfoService.getList();
    }
}
