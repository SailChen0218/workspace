package cn.com.servyou.service.impl;

import cn.com.servyou.common.domain.UserDo;
import cn.com.servyou.common.dto.ResultDto;
import cn.com.servyou.common.vo.UserVo;
import cn.com.servyou.dao.mydemo.IUserDao;
import cn.com.servyou.service.IUserService;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.ArrayList;
import java.util.List;

@Service
public class UserServiceImpl implements IUserService {

    @Autowired
    IUserDao userDao;

    @Override
    public ResultDto<List<UserVo>> getUserList() {
        List<UserDo> userDoList = userDao.getUserList();
        List<UserVo> userVoList = new ArrayList<>();

        for (UserDo userDo: userDoList ) {
            UserVo userVo = new UserVo();
            BeanUtils.copyProperties(userDo, userVo);
            userVoList.add(userVo);
        }

        return ResultDto.valueOfSuccess(userVoList);
    }
}
