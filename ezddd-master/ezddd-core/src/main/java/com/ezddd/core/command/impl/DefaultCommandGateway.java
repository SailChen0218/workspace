package com.ezddd.core.command.impl;

import com.ezddd.core.annotation.EzService;
import com.ezddd.core.command.*;
import com.ezddd.core.response.AppResult;
import org.springframework.util.Assert;

@EzService(interfaceType = CommandGateway.class)
public class DefaultCommandGateway extends AbstractCommandGateway {

    @Override
    public AppResult send(Command command) {
        Assert.notNull(command, "command must not be null.");
        CommandDefinition commandDefinition = commandRegistry.findCommandDefinition(command.getClass().getSimpleName());
        CommandBus<AppResult> commandBus = commandDefinition.getCommandBus();
        Assert.notNull(commandBus, "Commandbus not found.");
        return commandBus.dispatch(command);
    }
}
