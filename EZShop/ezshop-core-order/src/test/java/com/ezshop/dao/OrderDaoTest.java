package com.ezshop.dao;

import com.ezshop.AppTest;
import com.ezshop.common.domain.OrderDo;
import com.ezshop.dao.orderdb.IOrderDao;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class OrderDaoTest extends AppTest {

    @Autowired
    IOrderDao orderDao;

    @Test
    public void tesSelectByPrimaryKey() {
        OrderDo orderDo = orderDao.selectByPrimaryKey("001");
        Assert.assertNotNull(orderDo);
    }
}
