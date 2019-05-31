package com.ezddd.core.event.impl;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.event.EventBus;
import com.ezddd.core.event.EventBusRegistry;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import java.util.HashMap;
import java.util.Map;

@EzComponent
public class EventBusRegistryImpl implements EventBusRegistry {

    /**
     * Map of *EventBusClassName, CommandBus*
     */
    protected static Map<String, EventBusDefinition> eventBusDefinitionHolder = new HashMap<>(16);

    private DefaultListableBeanFactory beanFactory;

    @Override
    public EventBus findEventBus(Class<?> eventBusType) {
        String eventBusName = eventBusType.getName();
        if (eventBusDefinitionHolder.containsKey(eventBusName)) {
            EventBusDefinition eventBusDefinition = eventBusDefinitionHolder.get(eventBusName);
            EventBus eventBus = eventBusDefinition.getEventBus();
            if (eventBus == null) {
                eventBus = (EventBus) this.beanFactory.getBean(eventBusType);
                eventBusDefinition.setEventBus(eventBus);
            }
            return eventBus;
        } else {
            throw new IllegalArgumentException("EventBus not found, eventBusName:[" + eventBusName + "]. ");
        }
    }

    @Override
    public void registry(BeanFactory beanFactory) {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
        String[] beanNames = this.beanFactory.getBeanNamesForType(EventBus.class);
        if (beanNames != null && beanNames.length > 0) {
            GenericBeanDefinition beanDefinition = null;
            for (int i = 0; i < beanNames.length; i++) {
                beanDefinition = (GenericBeanDefinition) this.beanFactory.getBeanDefinition(beanNames[i]);
                EventBusDefinition eventBusDefinition = new EventBusDefinition();
                eventBusDefinition.setEventBusName(beanNames[i]);
                eventBusDefinition.setEventBus(null);
                eventBusDefinitionHolder.put(beanDefinition.getBeanClassName(), eventBusDefinition);
            }
        }
    }

    private class EventBusDefinition {
        private String eventBusName;
        private EventBus eventBus;

        public String getEventBusName() {
            return eventBusName;
        }

        public void setEventBusName(String eventBusName) {
            this.eventBusName = eventBusName;
        }

        public EventBus getEventBus() {
            return eventBus;
        }

        public void setEventBus(EventBus eventBus) {
            this.eventBus = eventBus;
        }
    }
}
