package com.ezshop.dao.userdb;

import com.ezshop.common.domain.UserDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

@Mapper
public interface IUserDao {
    int deleteByPrimaryKey(@Param("userId") String userId);

    int insert(@Param("record") UserDo record);

    int insertSelective(@Param("record") UserDo record);

    UserDo selectByPrimaryKey(@Param("userId") String userId);

    int updateByPrimaryKeySelective(@Param("record") UserDo record);

    int updateByPrimaryKey(@Param("record") UserDo record);
}
