package cn.com.servyou.service;

import cn.com.servyou.common.dto.ResultDto;
import cn.com.servyou.task.vo.TaskVo;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@FeignClient(value = "task")
@RequestMapping(value = "/task", produces = {"application/json;charset=UTF-8"})
public interface ITaskService {

    @RequestMapping(value = "/grdb", method = RequestMethod.POST)
    ResultDto<List<TaskVo>> queryTasksGrdb(Map parameters);

    @RequestMapping(value = "/server", method = RequestMethod.POST)
    ResultDto<Map<String, String>> getServerInfo();
}
