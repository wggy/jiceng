package com.linewell.jiceng.map.controller;

import com.linewell.jiceng.map.entity.UserInfoEntity;
import com.linewell.jiceng.map.service.UserInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
@RestController
@RequestMapping("/config")
@Api(tags = "测试服务")
public class ConfigController {


    @Value(value = "${useLocalCache: false}")
    private Boolean useLocalCache;

    @Autowired
    private UserInfoService userInfoService;


    @ApiOperation(value = "用户列表", notes = "演示表格树")
    @GetMapping("get")
    public List<UserInfoEntity> get() {
        return userInfoService.getList();
    }
}
