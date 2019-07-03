package com.ezshop.desensitize.spring;

import com.ezshop.desensitize.DesensitizeMethodHolder;
import com.ezshop.desensitize.Desensitized;
import com.ezshop.desensitize.util.ReflectionUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

@Component
public class DesensitizeBeanPostProcessor implements BeanPostProcessor {

    @Autowired
    DesensitizeMethodHolder desensitizeMethodHolder;

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        List<Method> interfaceMethodList = new ArrayList<>(8);
        ReflectionUtils.getInterfaceMethodsWithAnnotation(bean.getClass(), Desensitized.class, interfaceMethodList);
        for (Method method : interfaceMethodList) {
            String interfaceMethodName = method.getDeclaringClass().getName() + "." + method.getName();
            desensitizeMethodHolder.addMethod(interfaceMethodName, method);
        }
        return bean;
    }
}
