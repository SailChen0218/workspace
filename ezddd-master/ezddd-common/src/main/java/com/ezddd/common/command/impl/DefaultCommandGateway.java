package com.ezddd.common.command.impl;

import com.ezddd.common.annotation.EzComponent;
import com.ezddd.common.command.AbstractCommandGateway;
import com.ezddd.common.command.Command;
import com.ezddd.common.command.CommandBus;
import com.ezddd.common.response.AppResult;
import org.springframework.util.Assert;

@EzComponent
public class DefaultCommandGateway extends AbstractCommandGateway {

    @Override
    public AppResult send(Command command) {
        CommandBus<AppResult> commandBus = commandRegistry.findCommandBus(command);
        Assert.notNull(commandBus, "Commandbus not found.");
        return commandBus.dispatch(command);
    }
}
