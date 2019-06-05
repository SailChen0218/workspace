package com.ezddd.core.event.impl;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.annotation.EzEvent;
import com.ezddd.core.annotation.EzEventHandler;
import com.ezddd.core.event.AbstractEventDefinition;
import com.ezddd.core.event.Event;
import com.ezddd.core.event.EventListener;
import com.ezddd.core.event.EventRegistry;
import com.ezddd.core.spring.EzBeanFactoryPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@EzComponent
public class EventRegistryImpl implements EventRegistry {
    private static final Logger log = LoggerFactory.getLogger(EventRegistryImpl.class);
    private ConfigurableListableBeanFactory beanFactory;

    /**
     * Map of *eventName, EventHandler*
     */
    private Map<String, AbstractEventDefinition> eventDefinitionHolder = new HashMap<>();

    /**
     * Map of *eventClassName, EventListenerDefinition*
     */
    private Map<String, EventListenerDefinition> eventListenerDefinitionHolder = new HashMap<>();

    @Override
    public void registry(BeanFactory beanFactory) {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
        GenericBeanDefinition beanDefinition = null;
        String[] beanNames = this.beanFactory.getBeanNamesForAnnotation(EzEvent.class);
        if (beanNames != null && beanNames.length > 0) {
            for (int i = 0; i < beanNames.length; i++) {
                beanDefinition = (GenericBeanDefinition) this.beanFactory.getBeanDefinition(beanNames[i]);
                if (AbstractEventDefinition.class.isAssignableFrom(beanDefinition.getBeanClass())) {
                    this.registerEventDefinition(beanDefinition.getBeanClass());
                } else {
                    throw new IllegalArgumentException("EzEvent annotation should be used in " +
                            "the EventDefinition Class. ");
                }
            }
        }

        String[] eventListenerBeanNames = this.beanFactory.getBeanNamesForType(EventListener.class);
        if (eventListenerBeanNames != null && eventListenerBeanNames.length > 0) {
            for (int i = 0; i < eventListenerBeanNames.length; i++) {
                beanDefinition = (GenericBeanDefinition) this.beanFactory.getBeanDefinition(eventListenerBeanNames[i]);
                this.registerEventHandler(beanDefinition.getBeanClass());
            }
        }

        this.populateEventListenerInfo();
    }

    private void registerEventDefinition(Class<?> eventBeanType) {
        try {
            EzEvent ezEvent = eventBeanType.getAnnotation(EzEvent.class);
            if (ezEvent == null) {
                throw new IllegalArgumentException("eventBean should annotated by EzEvent. eventBeanType:"
                        + eventBeanType.getName());
            }

            Boolean isEventSourcing = ezEvent.eventSourcing();
            Class<?> eventBusType = ezEvent.eventBusType();

            AbstractEventDefinition eventDefinition = null;
            Field[] fields = eventBeanType.getFields();

            if (fields != null && fields.length > 0) {
                for (int i = 0; i < fields.length; i++) {
                    if (AbstractEventDefinition.class.isAssignableFrom(fields[i].getType())) {
                        eventDefinition = (AbstractEventDefinition) fields[i].get(null);
                        if (eventDefinition.isEventSourcing() == null) {
                            eventDefinition.setEventSourcing(isEventSourcing);
                        }
                        if (eventDefinition.getEventBusType() == null) {
                            eventDefinition.setEventBusType(eventBusType);
                        }
                        eventDefinition.setEventBeanType(eventBeanType);
                        eventDefinitionHolder.put(eventDefinition.getEventName(), eventDefinition);
                    }
                }
            }
        } catch (IllegalAccessException ex) {
            throw new IllegalArgumentException(ex.getMessage(), ex);
        }
    }

    private void registerEventHandler(Class<?> eventListenerType) {
        Method[] methods = eventListenerType.getMethods();
        if (methods != null && methods.length > 0) {
            for (int i = 0; i < methods.length; i++) {
                EzEventHandler ezCommandHandler = methods[i].getAnnotation(EzEventHandler.class);
                if (ezCommandHandler == null) {
                    continue;
                }

                Parameter[] parameters = methods[i].getParameters();
                if (parameters == null || parameters.length != 1) {
                    throw new IllegalArgumentException("There must be one and only one event type parameter of " +
                            "eventHandler:[" + methods[i].getName() + "]. ");
                }

                Class<?> eventClass = parameters[0].getType();
                if (!Event.class.isAssignableFrom(eventClass)) {
                    throw new IllegalArgumentException("There must be one Event type parameter of " +
                            "eventHandler:[" + methods[i].getName() + "]. ");
                }

                String eventName = methods[i].getName();
                if (eventListenerDefinitionHolder.containsKey(eventName)) {
                    EventListenerDefinition eventListenerDefinitionOld = eventListenerDefinitionHolder.get(eventName);
                    throw new IllegalArgumentException("The EventHandler has already exist. " +
                            "new-eventListener:[" + eventListenerType.getName() + "], new-eventHandlerMethod:[" + methods[i].getName() + "]. " +
                            "old-eventListener:[" + eventListenerDefinitionOld.getEventListener().getClass().getName() + "], old-eventHandlerMethod:[" +
                            eventListenerDefinitionOld.getMehtodOfHandler().getName() + "]. ");
                }

                EventListenerDefinition eventListenerDefinition = new EventListenerDefinition();
                eventListenerDefinition.setEventListener(null);
                eventListenerDefinition.setEventListenerType(eventListenerType);
                eventListenerDefinition.setMehtodOfHandler(methods[i]);
                eventListenerDefinitionHolder.put(eventName, eventListenerDefinition);
            }
        }
    }

    private void populateEventListenerInfo() {
        if (eventDefinitionHolder.size() > 0) {
            Set<String> eventDefinitionHolderKeys = eventDefinitionHolder.keySet();
            for (String eventDefinitionKey : eventDefinitionHolderKeys) {
                AbstractEventDefinition abstractEventDefinition = eventDefinitionHolder.get(eventDefinitionKey);
                EventListenerDefinition eventListenerDefinition = eventListenerDefinitionHolder.get(abstractEventDefinition.getEventName());
                if (eventListenerDefinition == null) {
                    throw new IllegalArgumentException(" Can not find the EventHandler of Event:[" +
                            abstractEventDefinition.getEventName() + "]. ");
                } else {
                    abstractEventDefinition.setEventListener(eventListenerDefinition.getEventListener());
                    abstractEventDefinition.setEventListenerType(eventListenerDefinition.getEventListenerType());
                    abstractEventDefinition.setMehtodOfHandler(eventListenerDefinition.getMehtodOfHandler());
                }
            }
        }
    }

    @Override
    public AbstractEventDefinition findEventDefinition(String eventName) {
        if (eventName == null) {
            throw new NullPointerException("eventName must not be null. ");
        }

        if (eventDefinitionHolder.containsKey(eventName)) {
            AbstractEventDefinition eventDefinition = eventDefinitionHolder.get(eventName);
            if (eventDefinition.getEventListener() == null) {
                EventListener eventListener =
                        (EventListener) this.beanFactory.getBean(eventDefinition.getEventListenerType());
                eventDefinition.setEventListener(eventListener);
            }
            return eventDefinition;
        } else {
            throw new IllegalArgumentException(
                    "There is no event named " + eventName + " exists. ");
        }
    }

    private final class EventListenerDefinition {
        private EventListener eventListener;
        private Method mehtodOfHandler;
        private Class<?> eventListenerType;

        public EventListener getEventListener() {
            return eventListener;
        }

        public void setEventListener(com.ezddd.core.event.EventListener eventListener) {
            this.eventListener = eventListener;
        }

        public Method getMehtodOfHandler() {
            return mehtodOfHandler;
        }

        public void setMehtodOfHandler(Method mehtodOfHandler) {
            this.mehtodOfHandler = mehtodOfHandler;
        }

        public Class<?> getEventListenerType() {
            return eventListenerType;
        }

        public void setEventListenerType(Class<?> eventListenerType) {
            this.eventListenerType = eventListenerType;
        }
    }
}
