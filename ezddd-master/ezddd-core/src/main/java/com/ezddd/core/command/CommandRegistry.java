package com.ezddd.core.command;

import com.ezddd.core.registry.Registry;

public interface CommandRegistry extends Registry {
    CommandDefinition findCommandDefinition(String commandName);
}
