package com.ezshop.dao;

import com.ezshop.AppTest;
import com.ezshop.common.domain.UserDo;
import com.ezshop.dao.userdb.IUserDao;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class UserDaoTest extends AppTest {

    @Autowired
    IUserDao userDao;

    @Test
    public void tesSelectByPrimaryKey() {
        UserDo userDo = userDao.selectByPrimaryKey("001");
        Assert.assertNotNull(userDo);
    }
}
