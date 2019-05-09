package com.ezddd.core.command.impl;

import com.ezddd.core.annotation.EzDomainService;
import com.ezddd.core.command.*;
import com.ezddd.core.response.CommandResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

@EzDomainService(interfaceType = CommandGateway.class)
public class DefaultCommandGateway<T> extends AbstractCommandGateway<T> {

    @Autowired
    CommandBusRegistry commandBusRegistry;

    @Override
    public CommandResult<T> send(Command command) {
        Assert.notNull(command, "command must not be null.");
        CommandBus commandBus = commandBusRegistry.findCommandBus(command.getClass().getName());
        Assert.notNull(commandBus, "Commandbus not found.");
        return commandBus.dispatch(command);
    }
}
