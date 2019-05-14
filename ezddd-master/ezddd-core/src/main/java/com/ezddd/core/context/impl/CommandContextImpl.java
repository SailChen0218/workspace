package com.ezddd.core.context.impl;

import com.ezddd.core.command.CommandDefinition;
import com.ezddd.core.context.CommandContext;
import com.ezddd.core.repository.Repository;

public class CommandContextImpl implements CommandContext {
    private Repository repository;
    private CommandDefinition commandDefinition;
    private Object response;

    public CommandContextImpl() {
    }

    public CommandContextImpl(Repository repository, CommandDefinition commandDefinition) {
        this.repository = repository;
        this.commandDefinition = commandDefinition;
    }

    @Override
    public Repository getRepository() {
        return this.repository;
    }

    @Override
    public void setRepository(Repository repository) {
        this.repository = repository;
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
    public <T> void setResponse(T value) {
        this.response = value;
    }

    @Override
    public <T> T getResponse() {
        return (T)this.response;
    }
}
