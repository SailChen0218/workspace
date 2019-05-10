package com.ezddd.core.event.impl;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.event.EventDefinition;
import com.ezddd.core.event.EventListener;
import com.ezddd.core.event.EventRegistry;
import com.ezddd.core.spring.EzBeanFactoryPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@EzComponent
public class EventRegistryImpl implements EventRegistry {
    private static final Logger log = LoggerFactory.getLogger(EzBeanFactoryPostProcessor.class);
    private ConfigurableListableBeanFactory beanFactory;

    // <eventName, EventHandler>
    private Map<String, EventDefinition> eventDefinitionHolder = new HashMap<>();

    @Override
    public void registry(BeanFactory beanFactory) {
        this.beanFactory = (ConfigurableListableBeanFactory)beanFactory;
        Map<String, EventListener> eventListenerMap = this.beanFactory.getBeansOfType(EventListener.class);
        for (EventListener eventListener : eventListenerMap.values()) {
            this.registerEventHandler(eventListener);
        }
    }

    public void registerEventHandler(EventListener eventListener) {
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

    @Override
    public EventDefinition findEventDefinition(String eventName) {
        return eventDefinitionHolder.get(eventName);
    }
}
