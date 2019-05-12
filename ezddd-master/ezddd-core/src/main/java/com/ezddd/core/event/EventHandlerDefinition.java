package com.ezddd.core.event;

import com.ezddd.core.annotation.EzEventHandler;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class EventHandlerDefinition {
    private String eventName;
    private EventListener eventListener;
    private Method mehtodOfHandler;
    private boolean eventSourcing;

    public static Map<String, EventDefinition> build(EventListener eventListener) {
        Assert.notNull(eventListener, "eventListener must not be null.");
        Map<String, EventDefinition> eventHandlerDefinitionMap = null;
        Class<?> clazz = eventListener.getClass();
        Method[] methods = clazz.getMethods();
        if (methods != null && methods.length > 0) {
            for (int i = 0; i < methods.length; i++) {
                EzEventHandler ezEventHandler = methods[i].getAnnotation(EzEventHandler.class);
                if (ezEventHandler != null) {
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

    public String getEventName() {
        return eventName;
    }

    public EventListener getEventListener() {
        return eventListener;
    }

    public Method getMehtodOfHandler() {
        return mehtodOfHandler;
    }

    public boolean isEventSourcing() {
        return eventSourcing;
    }
}
