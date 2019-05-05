package com.ezddd.core.event;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.spring.EzBeanFactoryPostProcessor;
import com.ezddd.core.registry.Registry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@EzComponent
public class EventRegistry implements Registry {
    private static final Logger log = LoggerFactory.getLogger(EzBeanFactoryPostProcessor.class);
    private ConfigurableListableBeanFactory beanFactory;

    // <eventName, EventHandler>
    private Map<String, EventHandlerDefinition> eventHandlerDefinitionHolder = new HashMap<>();

    @Override
    public void registry(BeanFactory beanFactory) {
        this.beanFactory = (ConfigurableListableBeanFactory)beanFactory;
        Map<String, EventListener> eventListenerMap = this.beanFactory.getBeansOfType(EventListener.class);
        for (EventListener eventListener : eventListenerMap.values()) {
            this.registerEventHandler(eventListener);
        }
    }

    EventHandlerDefinition findEventHandlerDefinition(String eventName) {
        return eventHandlerDefinitionHolder.get(eventName);
    }

    public void registerEventHandler(EventListener eventListener) {
        Map<String, EventHandlerDefinition> eventHandlerDefinitionMap = EventHandlerDefinition.build(eventListener);
        if (eventHandlerDefinitionMap != null) {
            Set<String> keys = eventHandlerDefinitionHolder.keySet();
            for(String newKey: eventHandlerDefinitionMap.keySet()) {
                if (keys.contains(newKey)) {
                    throw new IllegalArgumentException("event:[" + newKey + "] has already exist.");
                } else {
                    log.info("event:[" + newKey + "] registration have successfully completed.");
                    eventHandlerDefinitionHolder.put(newKey, eventHandlerDefinitionMap.get(newKey));
                }
            }
        }
    }
}
