package cn.com.servyou.dao;

import cn.com.servyou.AppBase;
import cn.com.servyou.dao.symh.ITaskDao;
import cn.com.servyou.task.vo.TaskVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class TaskDaoTest extends AppBase {

    @Autowired
    ITaskDao taskDao;

    @Test
    public void testQueryTasksGrdb() {
        Map parameters = new HashMap<>();
        parameters.put("swryDm", "111");
        parameters.put("swjgDm", "1112");
        parameters.put("yxswjgDm", "123");
        parameters.put("rwms", "");
        parameters.put("fqsjq", "");
        parameters.put("fqsjz", "");
        parameters.put("systemId", "");
        parameters.put("blsxq", "");
        parameters.put("blsxz", "");
        parameters.put("gt3SysName", "gt3");
        parameters.put("gt3Count", "0");
        parameters.put("gt3RwMs", "条任务");
        parameters.put("gt3BlUrl", "http://123123");
        List<TaskVo> taskVoList = taskDao.queryTasksGrdb(parameters);
        Assert.assertNotNull(taskVoList);
    }
}
