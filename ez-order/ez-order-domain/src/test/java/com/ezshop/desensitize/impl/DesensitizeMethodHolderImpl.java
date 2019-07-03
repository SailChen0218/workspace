package com.ezshop.desensitize.impl;

import com.ezshop.desensitize.DesensitizeMethodHolder;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DesensitizeMethodHolderImpl implements DesensitizeMethodHolder {
    private static final Map<String, Method> interfaceMethodMap = new ConcurrentHashMap<>(32);

    @Override
    public Method findMethodByName(String interfaceMethodName) {
        if (!interfaceMethodMap.containsKey(interfaceMethodName)) {
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
}
