package com.ezshop.desensitize;

import com.ezshop.desensitize.dto.ErrorDto;

import java.lang.reflect.Method;
import java.util.List;

public interface ValidateProcessor {

    /**
     * 验证方法参数，返回错误参数名称及错误提示信息。
     *
     * @param target          验证对象
     * @param targetMethod    验证方法
     * @param parameterValues 方法参数
     * @param parameterNames  方法参数名称
     * @param channel         渠道编号
     * @param service         交易服务编号
     * @return
     */
    List<ErrorDto> validateParameters(Object target,
                                      Method targetMethod,
                                      Object[] parameterValues,
                                      List<String> parameterNames,
                                      String channel,
                                      String service);
}
