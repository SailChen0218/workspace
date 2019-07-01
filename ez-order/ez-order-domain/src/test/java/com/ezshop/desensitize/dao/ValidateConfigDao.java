package com.ezshop.desensitize.dao;

import cn.com.servyou.bonde.gate.desensitize.dto.ParameterConfigDto;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface ValidateConfigDao {
    List<ParameterConfigDto> queryParameterConfigDto(@Param("channel") String channel, @Param("service") String service);
}
