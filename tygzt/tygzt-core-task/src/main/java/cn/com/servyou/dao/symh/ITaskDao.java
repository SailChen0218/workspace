package cn.com.servyou.dao.symh;

import cn.com.servyou.task.vo.TaskVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;
import java.util.Map;

@Mapper
public interface ITaskDao {
    List<TaskVo> queryTasksGrdb(Map parameters);
}
