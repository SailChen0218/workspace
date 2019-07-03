package com.ezshop.desensitize.impl;

import com.ezshop.desensitize.SensitiveType;
import com.ezshop.desensitize.SentitiveTypeFactory;
import com.ezshop.desensitize.exception.SensitizeTypeNotExistsException;
import com.ezshop.desensitize.spring.ApplicationContextUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.stereotype.Component;

import java.lang.reflect.Type;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class SentitiveTypeFactoryImpl implements SentitiveTypeFactory{
    private static final Logger LOG = LoggerFactory.getLogger(SentitiveTypeFactoryImpl.class);
    private static final Map<Type, SensitiveType> sensitiveTypeHolder = new ConcurrentHashMap<>(8);

    @Override
    public SensitiveType getSensitiveType(Class<? extends SensitiveType> clazz) throws SensitizeTypeNotExistsException {
        if (sensitiveTypeHolder.containsKey(clazz)) {
            return sensitiveTypeHolder.get(clazz);
        } else {
            try {
                SensitiveType sensitiveType = ApplicationContextUtil.getBean(clazz);
                sensitiveTypeHolder.put(clazz, sensitiveType);
                return sensitiveType;
            } catch (BeansException ex) {
                LOG.error("找不到脱敏类型为[{}]的Bean。", clazz.getName(), ex);
                throw new SensitizeTypeNotExistsException("找不到脱敏类型为[" + clazz.getName() + "]的Bean。");
            }
        }
    }
}
