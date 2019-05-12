package com.ezddd.core.event;

import com.ezddd.core.registry.Registry;

public interface EventBusRegistry extends Registry {
    EventBus findEventBus(String commandName);
}
