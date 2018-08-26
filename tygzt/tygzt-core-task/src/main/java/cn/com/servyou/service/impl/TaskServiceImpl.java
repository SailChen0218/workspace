package cn.com.servyou.service.impl;

import cn.com.servyou.common.dto.ResultDto;
import cn.com.servyou.dao.symh.ITaskDao;
import cn.com.servyou.service.ITaskService;
import cn.com.servyou.task.vo.TaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

//@Service
public class TaskServiceImpl implements ITaskService {

    @Autowired
    ITaskDao taskDao;

    @Override
    public ResultDto<List<TaskVo>> queryTasksGrdb(Map parameters) {
        List<TaskVo> taskVoList = taskDao.queryTasksGrdb(parameters);
        return ResultDto.valueOfSuccess(taskVoList);
    }

    @Override
    public ResultDto<Map<String, String>> getServerInfo() {
        HttpServletRequest req = ((ServletRequestAttributes) RequestContextHolder.getRequestAttributes()).getRequest();
        Map<String, String> serverInfo = new HashMap();
        serverInfo.put("ip", req.getRemoteAddr());
        serverInfo.put("port", String.valueOf(req.getRemotePort()));
        serverInfo.put("flg", "8180");
        return ResultDto.valueOfSuccess(serverInfo);
    }
}
