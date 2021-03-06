package com.ezddd.core.command.impl;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.command.AbstractCommandBus;
import com.ezddd.core.command.Command;
import com.ezddd.core.command.CommandHandler;
import com.ezddd.core.response.CommandResult;
import org.springframework.beans.factory.annotation.Autowired;

@EzComponent
public class DefaultCommandBus extends AbstractCommandBus {

    @Autowired
    CommandHandler commandHandler;

    @Override
    public CommandResult<?> dispatch(Command command) {
        return commandHandler.handle(command);
    }

}
