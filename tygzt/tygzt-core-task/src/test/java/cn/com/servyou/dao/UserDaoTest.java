package cn.com.servyou.dao;

import cn.com.servyou.AppBase;
import cn.com.servyou.common.domain.UserDo;
import cn.com.servyou.dao.mydemo.IUserDao;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class UserDaoTest extends AppBase {

    @Autowired
    IUserDao userDao;

    @Test
    public void testGetUserList() {
        List<UserDo> userDoList = userDao.getUserList();
        System.out.println("处理完成！！！");
    }
}
