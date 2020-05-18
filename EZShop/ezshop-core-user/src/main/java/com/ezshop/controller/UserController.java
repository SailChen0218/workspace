package com.ezshop.controller;

import cn.com.servyou.common.dto.ResultDto;
import com.ezshop.common.dto.UserDto;
import com.ezshop.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    IUserService userService;

    @RequestMapping(path="/queryUser", method = RequestMethod.POST)
    public ResultDto<UserDto> queryUser(String userId) {
        UserDto userDto = userService.queryUser(userId);
        if (userDto == null) {
            return ResultDto.valueOfError("查询不到用户信息。");
        }
        return ResultDto.valueOfSuccess(userDto);
    }
}
