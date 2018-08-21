package cn.com.servyou.controller;

import cn.com.servyou.common.dto.ResultDto;
import cn.com.servyou.service.ITaskService;
import cn.com.servyou.service.ITaskSynchronizerService;
import cn.com.servyou.task.synchronizer.vo.SystemVo;
import cn.com.servyou.task.vo.TaskVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TaskSynchronizerController {

    @Autowired
    ITaskSynchronizerService taskSynchronizerService;

    @Autowired
    ITaskService taskService;

    @RequestMapping(path="/systems", method = RequestMethod.GET)
    public ResultDto<List<SystemVo>> getSystems() {
        return taskSynchronizerService.getSystems();
    }

    @RequestMapping(path="/grdb", method = RequestMethod.GET)
    public ResultDto<List<TaskVo>> getTaskGrdb() throws UnsupportedEncodingException {
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
        parameters.put("gt3RwMs", URLEncoder.encode("条任务","UTF-8"));
        parameters.put("gt3BlUrl", "http://123123");
        return taskService.queryTasksGrdb(parameters);
    }

    @RequestMapping(path="/server", method = RequestMethod.GET)
    public ResultDto<Map<String, String>> getServerInfo() {
        return taskService.getServerInfo();
    }
}
