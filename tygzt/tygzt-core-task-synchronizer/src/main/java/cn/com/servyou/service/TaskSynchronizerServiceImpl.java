package cn.com.servyou.service;

import cn.com.servyou.common.dto.ResultDto;
import cn.com.servyou.dao.symh.ITaskSynchronizerDao;
import cn.com.servyou.task.synchronizer.vo.SystemVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class TaskSynchronizerServiceImpl implements ITaskSynchronizerService{
    private static final Log LOG = LogFactory.getLog(TaskSynchronizerServiceImpl.class.getName());

    @Autowired
    ITaskSynchronizerDao taskSynchronizerDao;

    @Override
    public ResultDto<List<SystemVo>> getSystems() {
        List<SystemVo> systemVoList = taskSynchronizerDao.getSystems();
        if (systemVoList == null) {
            return ResultDto.valueOfError("查询不到同步对象配置信息。");
        }
        LOG.error("测试错误日志。");
        LOG.info("查询到同步对象配置信息" + systemVoList.size() + "条。");
        return ResultDto.valueOfSuccess(systemVoList);
    }
}
