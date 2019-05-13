package com.ezddd.core.event;

import com.ezddd.core.registry.Registry;

public interface EventRegistry extends Registry {
    AbstractEventDefinition findEventDefinition(String eventName);
}
