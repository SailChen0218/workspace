package com.ezshop.common.domain;

import lombok.Data;

import java.util.Date;

@Data
public class UserDo {
    private String userId;

    private String userName;

    private String nickName;

    private String realName;

    private Date birthday;

    private String telPhone;

    private String email;

    private String address;

    private Integer grade;

    private String password;

    private Integer isDeleted;

    private Date createDate;

    private Date updateDate;
}