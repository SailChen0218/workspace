package cn.com.servyou.dao;

import cn.com.servyou.AppBase;
import cn.com.servyou.dao.symh.ITaskSynchronizerDao;
import cn.com.servyou.task.synchronizer.vo.SystemVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.util.List;

@EnableTransactionManagement
public class TaskSynchronizerDaoTest extends AppBase {
    @Autowired
    ITaskSynchronizerDao taskSynchronizerDao;

    @Test
    public void testGetSystems(){
        List<SystemVo> systemVoList = taskSynchronizerDao.getSystems();
        Assert.assertNotNull(systemVoList);
    }

}
