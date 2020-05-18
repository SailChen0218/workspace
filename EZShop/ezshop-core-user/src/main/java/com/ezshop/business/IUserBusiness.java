package com.ezshop.business;

import com.ezshop.common.dto.UserDto;

public interface IUserBusiness {
    UserDto queryUser(String userId);
}
