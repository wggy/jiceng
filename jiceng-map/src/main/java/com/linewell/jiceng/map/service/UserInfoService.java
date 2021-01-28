package com.linewell.jiceng.map.service;

import com.google.common.collect.Lists;
import com.linewell.jiceng.map.entity.UserInfoEntity;
import com.linewell.jiceng.map.repository.UserInfoRepository;
import org.springframework.stereotype.Service;

import java.util.List;

/***
 *  @author wping created on 2021-01-28 18:48
 */
@Service
public class UserInfoService {

    private final UserInfoRepository userInfoRepository;

    public UserInfoService(final UserInfoRepository userInfoRepository) {
        this.userInfoRepository = userInfoRepository;
    }


    public List<UserInfoEntity> getList() {
        return Lists.newArrayList(this.userInfoRepository.findAll());
    }
}
