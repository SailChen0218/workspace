package com.ezddd.core.event.impl;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.annotation.EzEvent;
import com.ezddd.core.annotation.EzEventHandler;
import com.ezddd.core.event.AbstractEventDefinition;
import com.ezddd.core.event.EventListener;
import com.ezddd.core.event.EventRegistry;
import com.ezddd.core.spring.EzBeanFactoryPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.util.Assert;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@EzComponent
public class EventRegistryImpl implements EventRegistry {
    private static final Logger log = LoggerFactory.getLogger(EzBeanFactoryPostProcessor.class);
    private ConfigurableListableBeanFactory beanFactory;

    // <eventName, EventHandler>
    private Map<String, AbstractEventDefinition> eventDefinitionHolder = new HashMap<>();

    @Override
    public void registry(BeanFactory beanFactory) {
        this.beanFactory = (ConfigurableListableBeanFactory)beanFactory;
        GenericBeanDefinition beanDefinition = null;
        String[] beanNames = this.beanFactory.getBeanNamesForAnnotation(EzEvent.class);
        if (beanNames != null && beanNames.length > 0) {
            for (int i = 0; i < beanNames.length; i++) {
                beanDefinition = (GenericBeanDefinition)this.beanFactory.getBeanDefinition(beanNames[i]);
                if (AbstractEventDefinition.class.isAssignableFrom(beanDefinition.getBeanClass())) {
                    this.registerEvent((EventDefinition)bean);
                } else {
                    throw new IllegalArgumentException("EzEvent annotation should " +
                            "be used in the EventDefinition Class. ");
                }
            }
        }
        for (EventListener eventListener : eventListenerMap.values()) {
            this.registerEventHandler(eventListener);
        }

        Map<String, EventListener> eventListenerMap = this.beanFactory.getBeansOfType(EventListener.class);
        for (EventListener eventListener : eventListenerMap.values()) {
            this.registerEventHandler(eventListener);
        }
    }

    private void registerEvent(EventDefinition eventDefinition) {
        Class<?> clazz = eventDefinition.getClass();
        Field[] fields = clazz.getFields();
    }

    private void registerEventHandler(EventListener eventListener) {
        Map<String, EventDefinition> eventDefinitionMap = EventDefinition.build(eventListener);
        if (eventDefinitionMap != null) {
            Set<String> keys = eventDefinitionHolder.keySet();
            for(String newKey: eventDefinitionMap.keySet()) {
                if (keys.contains(newKey)) {
                    throw new IllegalArgumentException("event:[" + newKey + "] has already exist.");
                } else {
                    log.info("event:[" + newKey + "] registration have successfully completed.");
                    eventDefinitionHolder.put(newKey, eventDefinitionMap.get(newKey));
                }
            }
        }
    }

    private Map<String, EventDefinition> build(EventListener eventListener) {
        Assert.notNull(eventListener, "eventListener must not be null.");
        Map<String, EventDefinition> eventHandlerDefinitionMap = null;
        Class<?> clazz = eventListener.getClass();
        Method[] methods = clazz.getMethods();
        if (methods != null && methods.length > 0) {
            for (int i = 0; i < methods.length; i++) {
                EzEventHandler ezEventHandler = methods[i].getAnnotation(EzEventHandler.class);
                if (ezEventHandler != null) {
                    String eventName = methods[i].getName();
                    EventDefinition eventDefinition = new EventDefinition();
                    eventDefinition.eventName = methods[i].getName();
                    eventDefinition.eventListener = eventListener;
                    eventDefinition.mehtodOfHandler = methods[i];
                    eventDefinition.eventSourcing = ezEventHandler.eventSourcing();
                    if (eventHandlerDefinitionMap == null) {
                        eventHandlerDefinitionMap = new HashMap<>();
                    }

                    if (eventHandlerDefinitionMap.containsKey(eventDefinition.eventName)) {
                        throw new IllegalArgumentException("event:[" + eventDefinition.eventName
                                + "] has already exist.");
                    } else {
                        eventHandlerDefinitionMap.put(eventDefinition.eventName, eventDefinition);
                    }
                }
            }
        }
        return eventHandlerDefinitionMap;
    }

    @Override
    public EventDefinition findEventDefinition(String eventName) {
        if (eventName == null) {
            throw new NullPointerException("eventName must not be null. ");
        }

        EventDefinition result = eventDefinitionHolder.get(eventName);
        if (result != null) {
            return result;
        }

        throw new IllegalArgumentException(
                "There is no event named " + eventName + " exists. ");
    }
}
