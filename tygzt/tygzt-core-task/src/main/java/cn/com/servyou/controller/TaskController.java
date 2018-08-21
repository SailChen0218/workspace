package cn.com.servyou.controller;

import cn.com.servyou.common.dto.ResultDto;
import cn.com.servyou.service.ITaskService;
import cn.com.servyou.task.vo.TaskVo;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.discovery.DiscoveryClient;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
public class TaskController {
    private static final Log LOG = LogFactory.getLog(TaskController.class.getName());

    @Autowired
    ITaskService taskService;

    @RequestMapping(path="/grdb", method = RequestMethod.POST)
    public ResultDto<List<TaskVo>> getTaskGrdb() {
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
        return taskService.queryTasksGrdb(parameters);
    }

    @RequestMapping(path="/server", method = RequestMethod.POST)
    public ResultDto<Map<String, String>> getServerInfo() {
        return taskService.getServerInfo();
    }
}