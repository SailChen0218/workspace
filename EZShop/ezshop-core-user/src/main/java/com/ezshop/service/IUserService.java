package com.ezshop.service;

import com.ezshop.common.dto.UserDto;

public interface IUserService {
    UserDto queryUser(String userId);
}
