package com.ezddd.core.context.impl;

import com.ezddd.core.command.CommandDefinition;
import com.ezddd.core.context.CommandContext;

public class CommandContextImpl<T> implements CommandContext<T> {
    private CommandDefinition commandDefinition;
    private T aggregateRoot;

    public CommandContextImpl() {
    }

    public CommandContextImpl(CommandDefinition commandDefinition, T aggregateRoot) {
        this.aggregateRoot = aggregateRoot;
        this.commandDefinition = commandDefinition;
    }

    @Override
    public void setCommandDefinition(CommandDefinition commandDefinition) {
        this.commandDefinition = commandDefinition;
    }

    @Override
    public CommandDefinition getCommandDefinition() {
        return this.commandDefinition;
    }

    @Override
    public void setAggregateRoot(T value) {

    }

    @Override
    public T getAggregateRoot() {
        return null;
    }
}
