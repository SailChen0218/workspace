package com.ezshop.desensitize.impl;

import com.ezshop.desensitize.DesensitizeMethodHolder;
import com.ezshop.desensitize.dto.MethodResovingDto;
import com.ezshop.desensitize.util.ClassPropertyTreeNode;
import com.ezshop.desensitize.util.ClassPropertyTreeUtils;
import com.ezshop.desensitize.util.ReflectionUtils;
import com.ezshop.test.ResultDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DesensitizeMethodHolderImpl implements DesensitizeMethodHolder {
    private static final Logger log = LoggerFactory.getLogger(DesensitizeMethodHolderImpl.class);
    private static final Map<String, Method> interfaceMethodMap = new ConcurrentHashMap<>(32);

    @Override
    public Method findMethodByName(String interfaceMethodName) {
        if (interfaceMethodMap.containsKey(interfaceMethodName)) {
            return interfaceMethodMap.get(interfaceMethodName);
        }
        return null;
    }

    @Override
    public boolean addMethod(String interfaceMethodName, Method method) {
        if (!interfaceMethodMap.containsKey(interfaceMethodName)) {
            interfaceMethodMap.put(interfaceMethodName, method);
            return true;
        } else {
            return false;
        }
    }

    @Override
    public ResultDto<MethodResovingDto> resolveMethod(String interfaceMethodName) {
        try {
            Method method = this.findMethodByName(interfaceMethodName);
            if (method == null) {
                return ResultDto.valueOfError("接口方法没有找到。interfaceMethodName:" + interfaceMethodName);
            }
            ClassPropertyTreeNode treeNode = ClassPropertyTreeUtils.parseMethodReturnType(method);
            List<String> parameterNames = ReflectionUtils.getMethodParameterNames(method);
            MethodResovingDto methodResovingDto = new MethodResovingDto();
            methodResovingDto.setTreeNode(treeNode);
            methodResovingDto.setParameters(parameterNames);
            return ResultDto.valueOfSuccess(methodResovingDto);
        } catch (Exception ex) {
            log.error(ex.getMessage(), ex);
            return ResultDto.valueOfError(ex.getMessage());
        }
    }
}
