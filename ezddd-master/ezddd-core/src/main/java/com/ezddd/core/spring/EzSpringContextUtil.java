package com.ezddd.core.spring;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class EzSpringContextUtil implements ApplicationContextAware {
    private static final Logger LOG = LoggerFactory.getLogger(EzSpringContextUtil.class);
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext context) throws BeansException {
        this.applicationContext = context;
    }
}
