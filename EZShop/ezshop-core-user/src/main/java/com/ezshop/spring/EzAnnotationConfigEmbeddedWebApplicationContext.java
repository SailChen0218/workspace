≤package com.ezshop.spring;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.boot.context.embedded.AnnotationConfigEmbeddedWebApplicationContext;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;
import org.springframework.web.context.support.XmlWebApplicationContext;

public class EzAnnotationConfigEmbeddedWebApplicationContext extends AnnotationConfigEmbeddedWebApplicationContext {

    @Override
    protected BeanFactory getInternalParentBeanFactory() {
        return (getParent() instanceof ConfigurableApplicationContext) ?
                ((ConfigurableApplicationContext) getParent()).getBeanFactory() : getParent();
    }

}
