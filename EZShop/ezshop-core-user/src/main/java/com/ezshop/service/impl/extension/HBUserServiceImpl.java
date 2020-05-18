package com.ezshop.service.impl.extension;

import com.ezshop.annotations.EzExtension;
import com.ezshop.common.dto.UserDto;
import com.ezshop.service.impl.UserServiceImpl;
import org.springframework.context.annotation.Conditional;

import javax.annotation.Priority;

@EzExtension(area = "hn", priority = 1)
@Priority(1)
public class HBUserServiceImpl extends UserServiceImpl {
    @Override
    public UserDto queryUser(String userId) {
        UserDto userDto = super.queryUser(userId);
        userDto.setNickName("hb nickname");
        return userDto;
    }
}
