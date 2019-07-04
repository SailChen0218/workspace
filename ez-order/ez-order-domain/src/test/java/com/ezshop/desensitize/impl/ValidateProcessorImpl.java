package com.ezshop.desensitize.impl;

import com.ezshop.desensitize.ValidateProcessor;
import com.ezshop.desensitize.dto.ErrorDto;
import com.ezshop.desensitize.dto.ParameterConfigDto;
import com.ezshop.desensitize.util.MessageConvertor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.*;

@Component
public class ValidateProcessorImpl implements ValidateProcessor {
    private static final Logger log = LoggerFactory.getLogger(ValidateProcessorImpl.class);

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
                                             List<String> parameterNames,
                                             String channel,
                                             String service) {
        // 校验入参格式
        List<ErrorDto> errorDtoList = new ArrayList<>(8);
        Set<ConstraintViolation<Object>> constraintViolationSet =
                getExecutableValidator().validateParameters(target, method, parameterValues);
        if (constraintViolationSet != null && constraintViolationSet.size() > 0) {
            for (ConstraintViolation<Object> constraintViolation : constraintViolationSet) {
                errorDtoList.add(new ErrorDto(constraintViolation.getPropertyPath().toString(),
                        MessageConvertor.convert(constraintViolation.getMessage())));
            }
        }

        // 校验入参不能为空
        validateNotNullParameters(errorDtoList, method, parameterValues, parameterNames, channel, service);

        return errorDtoList;
    }

    /**
     * 根据数据库里的配置信息校验入参不能为空。
     *
     * @param errorDtoList
     * @param parameterValues
     * @param parameterNames
     * @param channel
     * @param service
     * @return
     */
    private void validateNotNullParameters(List<ErrorDto> errorDtoList,
                                           Method method,
                                           Object[] parameterValues,
                                           List<String> parameterNames,
                                           String channel,
                                           String service) {
        // 入参存在时进行参数非空校验
        if (parameterValues != null && parameterValues.length > 0) {
            if (CollectionUtils.isEmpty(parameterNames) || parameterNames.size() != parameterValues.length) {
                log.error("带ApiParam注解的方法参数个数与请求参数个数不一致，请检查脱敏接口方法的入参注解。" +
                                "Class[{}],Method[{}]", method.getDeclaringClass().getName(), method.getName());
                return;
            }
            Map<String, Integer> parameterConfigMap = getParameterConfig(channel, service);
            for (int i = 0; i < parameterValues.length; i++) {
                String parameterName = parameterNames.get(i);
                if (parameterConfigMap.containsKey(parameterName)) {
                    int configValue = parameterConfigMap.get(parameterName);
                    // 0:非必须 1:必须
                    if (configValue == 1 && parameterValues[i] == null) {
                        errorDtoList.add(new ErrorDto(parameterName, parameterName + "参数不能为空。"));
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
        parameterConfigDto1.setConfigValue(1);
        parameterConfigDtoList.add(parameterConfigDto1);

        ParameterConfigDto parameterConfigDto2 = new ParameterConfigDto();
        parameterConfigDto2.setName("email");
        parameterConfigDto2.setConfigValue(1);
        parameterConfigDtoList.add(parameterConfigDto2);

        Map<String, Integer> parameterConfigMap = new HashMap<>(parameterConfigDtoList.size());
        for (ParameterConfigDto parameterConfigDto : parameterConfigDtoList) {
            parameterConfigMap.put(parameterConfigDto.getName(), parameterConfigDto.getConfigValue());
        }

        //TODO
        return parameterConfigMap;
    }
}
