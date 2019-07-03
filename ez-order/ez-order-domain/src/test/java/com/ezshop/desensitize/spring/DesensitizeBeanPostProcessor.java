package com.ezshop.desensitize.spring;

import com.ezshop.desensitize.Desensitized;
import com.ezshop.desensitize.util.ReflectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class DesensitizeBeanPostProcessor implements BeanPostProcessor {
    private static final Map<String, Method> classMethodMap = new ConcurrentHashMap<>(32);

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        List<Method> interfaceMethodList = new ArrayList<>(8);
        ReflectionUtils.getInterfaceMethodsWithAnnotation(bean.getClass(), Desensitized.class, interfaceMethodList);
        for (Method method : interfaceMethodList) {
            String classMethod = method.getDeclaringClass().getName() + "." + method.getName();
            if (!classMethodMap.containsKey(classMethod)) {
                classMethodMap.put(classMethod, method);
            }
        }
        return bean;
    }

    /**
     * 根据类方法名获取对应的Method对象。
     *
     * @param classMethodName 例：gov.etax.dzswj.sbzx.service.sb.fpxxtq.IFpxxTqService.queryNsrgsxxByShxydm
     * @return
     */
    public Method findeMethod(String classMethodName) {
        if (classMethodMap.containsKey(classMethodName)) {
            return classMethodMap.get(classMethodName);
        }
        return null;
    }
}
