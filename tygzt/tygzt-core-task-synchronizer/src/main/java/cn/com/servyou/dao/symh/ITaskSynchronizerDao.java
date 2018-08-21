package cn.com.servyou.dao.symh;

import cn.com.servyou.task.synchronizer.vo.SystemVo;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface ITaskSynchronizerDao {
    List<SystemVo> getSystems();
}
