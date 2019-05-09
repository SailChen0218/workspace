package com.ezddd.core.command;

import com.ezddd.core.registry.Registry;

public interface CommandBusRegistry extends Registry {
    CommandBus findCommandBus(String commandName);
}
