package cn.com.servyou.service;

import cn.com.servyou.common.dto.ResultDto;
import cn.com.servyou.task.synchronizer.vo.SystemVo;

import java.util.List;

public interface ITaskSynchronizerService {
    /**
     * 查询要同步任务数据的子系统配置信息。
     * @return
     */
    ResultDto<List<SystemVo>> getSystems();
}
