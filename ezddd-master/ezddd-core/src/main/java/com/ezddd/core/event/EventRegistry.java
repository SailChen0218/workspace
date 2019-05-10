package com.ezddd.core.event;

import com.ezddd.core.registry.Registry;

public interface EventRegistry extends Registry {
    EventDefinition findEventDefinition(String eventName);
}
