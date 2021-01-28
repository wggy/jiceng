package com.linewell.jiceng.map.repository;

import com.linewell.jiceng.map.entity.UserInfoEntity;
import org.springframework.data.repository.PagingAndSortingRepository;

/***
 *  @author wping created on 2021-01-28 18:46
 */
public interface UserInfoRepository extends PagingAndSortingRepository<UserInfoEntity, Long> {
}
