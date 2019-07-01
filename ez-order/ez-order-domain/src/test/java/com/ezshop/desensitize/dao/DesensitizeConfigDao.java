package com.ezshop.desensitize.dao;


import com.ezshop.desensitize.dto.DesensitizeConfigDto;

import java.util.List;

public interface DesensitizeConfigDao {
    List<DesensitizeConfigDto> queryDesensitizeConfig(@Param("channel") String channel,
                                                      @Param("service") String service);
}
