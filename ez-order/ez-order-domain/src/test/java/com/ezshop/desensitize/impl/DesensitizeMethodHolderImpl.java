package com.ezshop.desensitize.impl;

import com.ezshop.desensitize.DesensitizeMethodHolder;
import com.ezshop.desensitize.dto.MethodResovingDto;
import com.ezshop.desensitize.util.ClassPropertyTreeNode;
import com.ezshop.desensitize.util.ClassPropertyTreeUtils;
import com.ezshop.desensitize.util.ReflectionUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DesensitizeMethodHolderImpl implements DesensitizeMethodHolder {
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
    public MethodResovingDto resolveMethod(String interfaceMethodName) {
        Method method = this.findMethodByName(interfaceMethodName);
        if (method == null) {
            return null;
        }
        ClassPropertyTreeNode treeNode = ClassPropertyTreeUtils.parseMethodReturnType(method);
        List<String> parameterNames = ReflectionUtils.getMethodParameterNames(method);
        MethodResovingDto methodResovingDto = new MethodResovingDto();
        methodResovingDto.setTreeNode(treeNode);
        methodResovingDto.setParameters(parameterNames);
        return methodResovingDto;
    }
}
