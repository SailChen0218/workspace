package com.ezshop.service.impl;

import com.ezshop.business.IUserBusiness;
import com.ezshop.common.dto.UserDto;
import com.ezshop.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.Serializable;

@Service
public class UserServiceImpl implements IUserService, Serializable {
    @Autowired
    IUserBusiness userBusiness;

    @Override
    public UserDto queryUser(String userId) {
        return userBusiness.queryUser(userId);
    }
}
