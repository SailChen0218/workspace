package main.resources.mapper;

import main.java.com.ezshop.domain.UserDo;
import org.apache.ibatis.annotations.Param;

public interface UserDoMapper {
    int deleteByPrimaryKey(@Param("userId") String userId);

    int insert(@Param("record") UserDo record);

    int insertSelective(@Param("record") UserDo record);

    UserDo selectByPrimaryKey(@Param("userId") String userId);

    int updateByPrimaryKeySelective(@Param("record") UserDo record);

    int updateByPrimaryKey(@Param("record") UserDo record);
}