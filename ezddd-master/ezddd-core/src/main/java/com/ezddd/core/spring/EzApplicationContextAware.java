package com.ezddd.core.spring;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.event.Event;
import com.ezddd.core.event.EventBus;
import com.ezddd.core.registry.Registry;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.AbstractBeanDefinition;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

@EzComponent
public class EzApplicationContextAware implements ApplicationContextAware {

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
//        DefaultListableBeanFactory beanFactory =
//                (DefaultListableBeanFactory)applicationContext.getAutowireCapableBeanFactory();
//        beanFactory.registerResolvableDependency(EventBus.class, null);
//        beanFactory.registerResolvableDependency(Registry.class, null);
    }
}
