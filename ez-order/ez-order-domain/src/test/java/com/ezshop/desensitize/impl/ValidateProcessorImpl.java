package com.ezshop.desensitize.impl;

import com.ezshop.desensitize.ValidateProcessor;
import com.ezshop.desensitize.dto.ErrorDto;
import com.ezshop.desensitize.dto.ParameterConfigDto;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.*;

@Component
public class ValidateProcessorImpl implements ValidateProcessor {
    private static class ValidatorSingleton {
        private final ValidatorFactory factory;
        private final ExecutableValidator executableValidator;

        private ValidatorSingleton() {
            this.factory = Validation.buildDefaultValidatorFactory();
            this.executableValidator = factory.getValidator().forExecutables();
        }

        private static final ValidatorSingleton instance = new ValidatorSingleton();
    }

    private static ExecutableValidator getExecutableValidator() {
        return ValidatorSingleton.instance.executableValidator;
    }

    @Override
    public List<ErrorDto> validateParameters(Object target,
                                             Method method,
                                             Object[] parameterValues,
                                             String[] argNames,
                                             String channel,
                                             String service) {
        // 校验入参格式
        List<ErrorDto> errorDtoList = new ArrayList<>(8);
        Set<ConstraintViolation<Object>> constraintViolationSet =
                getExecutableValidator().validateParameters(target, method, parameterValues);
        if (constraintViolationSet != null && constraintViolationSet.size() > 0) {
            for (ConstraintViolation<Object> constraintViolation : constraintViolationSet) {
                errorDtoList.add(new ErrorDto(constraintViolation.getPropertyPath().toString(),
                        constraintViolation.getMessage()));
            }
        }

        // 校验入参不能为空
        validateNotNullParameters(errorDtoList, parameterValues, argNames, channel, service);

        return errorDtoList;
    }

    /**
     * 根据数据库里的配置信息校验入参不能为空。
     *
     * @param errorDtoList
     * @param parameterValues
     * @param argNames
     * @param channel
     * @param service
     * @return
     */
    private void validateNotNullParameters(List<ErrorDto> errorDtoList,
                                           Object[] parameterValues,
                                           String[] argNames,
                                           String channel,
                                           String service) {
        // 入参存在时进行参数非空校验
        if (parameterValues != null && parameterValues.length > 0) {
            Map<String, Integer> parameterConfigMap = getParameterConfig(channel, service);
            for (int i = 0; i < parameterValues.length; i++) {
                if (parameterConfigMap.containsKey(argNames[i])) {
                    int configValue = parameterConfigMap.get(argNames[i]);
                    // 0:非必须 1:必须
                    if (configValue == 1 && parameterValues[i] == null) {
                        errorDtoList.add(new ErrorDto(argNames[i], argNames[i] + "参数不能为空。"));
                    }
                }
            }
        }
    }

    /**
     * 查询参数配置信息
     *
     * @param channel 渠道编号
     * @param service 交易服务编号
     * @return
     */
    private Map<String, Integer> getParameterConfig(String channel, String service) {
        List<ParameterConfigDto> parameterConfigDtoList = new ArrayList<>();
        ParameterConfigDto parameterConfigDto1 = new ParameterConfigDto();
        parameterConfigDto1.setName("djxh");
        parameterConfigDto1.setConfigValue(0);
        parameterConfigDtoList.add(parameterConfigDto1);

        ParameterConfigDto parameterConfigDto2 = new ParameterConfigDto();
        parameterConfigDto2.setName("email");
        parameterConfigDto2.setConfigValue(0);
        parameterConfigDtoList.add(parameterConfigDto2);

        Map<String, Integer> parameterConfigMap = new HashMap<>(parameterConfigDtoList.size());
        for (ParameterConfigDto parameterConfigDto : parameterConfigDtoList) {
            parameterConfigMap.put(parameterConfigDto.getName(), parameterConfigDto.getConfigValue());
        }

        //TODO
        return parameterConfigMap;
    }
}
