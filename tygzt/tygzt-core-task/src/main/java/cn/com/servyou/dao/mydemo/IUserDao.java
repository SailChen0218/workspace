package cn.com.servyou.dao.mydemo;

import cn.com.servyou.common.domain.UserDo;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface IUserDao {
    List<UserDo> getUserList();
    UserDo getUser(@Param("id") String id);
}
