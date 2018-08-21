package cn.com.servyou.service;

import cn.com.servyou.AppBase;
import cn.com.servyou.common.dto.ResultDto;
import cn.com.servyou.task.synchronizer.vo.SystemVo;
import org.junit.Assert;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@EnableTransactionManagement
public class TaskSynchronizerServiceImplTest extends AppBase {

    @Autowired
    ITaskSynchronizerService taskSynchronizerService;

    @Test
    @Transactional(value = "symhTransactionManager", readOnly = true, propagation = Propagation.NOT_SUPPORTED)
    public void testGetSystems(){
        ResultDto<List<SystemVo>> resultDto = taskSynchronizerService.getSystems();
        Assert.assertTrue(resultDto.isSuccess());
    }
}
