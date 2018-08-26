package cn.com.servyou.service;

import cn.com.servyou.common.dto.ResultDto;
import cn.com.servyou.common.vo.UserVo;

import java.util.List;

public interface IUserService {
    ResultDto<List<UserVo>> getUserList();
}
