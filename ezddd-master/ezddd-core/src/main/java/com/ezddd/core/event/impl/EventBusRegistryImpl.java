package com.ezddd.core.event.impl;

import com.ezddd.core.command.CommandBus;
import com.ezddd.core.event.EventBus;
import com.ezddd.core.event.EventBusRegistry;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import java.util.HashMap;
import java.util.Map;

public class EventBusRegistryImpl implements EventBusRegistry {

    /**
     * Map of *EventBusClassName, CommandBus*
     */
    protected static Map<String, EventBus> eventBusHolder = new HashMap<>(16);

    @Override
    public EventBus findEventBus(String eventName) {
        return eventBusHolder.get(eventName);
    }

    @Override
    public void registry(BeanFactory beanFactory) {
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;
        String[] beanNames = defaultListableBeanFactory.getBeanNamesForType(EventBus.class);

        GenericBeanDefinition beanDefinition = null;
        if (beanNames != null && beanNames.length > 0) {
            for (int i = 0; i < beanNames.length; i++) {
                beanDefinition = (GenericBeanDefinition)defaultListableBeanFactory.getBeanDefinition(beanNames[i]);
                EventBus eventBus = beanFactory.getBean(beanNames[i], EventBus.class);
                eventBusHolder.put(beanDefinition.getBeanClassName(), eventBus);
            }
        }
    }
}
