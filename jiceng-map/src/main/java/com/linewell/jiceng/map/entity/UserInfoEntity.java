package com.linewell.jiceng.map.entity;

import lombok.Data;

import javax.persistence.*;

/***
 *  @author wping created on 2021-01-28 18:43
 */
@Data
@Entity
@Table(name = "user_info")
public class UserInfoEntity {


    @Id
    @GeneratedValue
    @Column(name = "id")
    private Long id;

    @Column(name = "username")
    private String username;


    @Column(name = "password")
    private String password;
}
