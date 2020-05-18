package com.ezshop.business.impl;

import com.ezshop.business.IUserBusiness;
import com.ezshop.common.domain.UserDo;
import com.ezshop.common.dto.UserDto;
import com.ezshop.dao.userdb.IUserDao;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserBusinessImpl implements IUserBusiness {

    @Autowired
    IUserDao userDao;

    @Override
    public UserDto queryUser(String userId) {
        UserDo userDo = userDao.selectByPrimaryKey(userId);
        UserDto userDto = null;
        if (userDo != null) {
            userDto = new UserDto();
            BeanUtils.copyProperties(userDo, userDto);
        }
        return userDto;
    }
}
