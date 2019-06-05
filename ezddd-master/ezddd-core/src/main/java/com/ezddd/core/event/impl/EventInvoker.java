package com.ezddd.core.event.impl;

import com.ezddd.core.event.Event;
import com.ezddd.core.event.EventDefinition;
import com.ezddd.core.event.EventInvokeException;
import com.ezddd.core.event.EventListener;

import java.lang.reflect.Method;

public class EventInvoker {
    public static void handle(Event event, EventDefinition eventDefinition) throws EventInvokeException {
        try {
            EventListener eventListener = eventDefinition.getEventListener();
            Method method = eventDefinition.getMehtodOfHandler();
            method.invoke(eventListener, event);
        } catch (Exception e) {
            throw new EventInvokeException(e);
        }
    }
}
