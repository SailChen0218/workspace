package com.ezddd.core.event;

import com.ezddd.core.registry.Registry;

public interface EventRegistry extends Registry {
    EventHandlerDefinition findEventHandlerDefinition(String eventName);
}
