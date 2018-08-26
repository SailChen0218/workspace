package cn.com.servyou.controller;

import cn.com.servyou.common.dto.ResultDto;
import cn.com.servyou.common.vo.UserVo;
import cn.com.servyou.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class UserController {

    @Autowired
    IUserService userService;

    @RequestMapping(path="/list/user", method = RequestMethod.POST)
    public ResultDto<List<UserVo>> getUserList() {
        return userService.getUserList();
    }
}
