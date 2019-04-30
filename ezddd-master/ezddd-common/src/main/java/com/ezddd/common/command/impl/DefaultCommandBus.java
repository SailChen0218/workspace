package com.ezddd.common.command.impl;

import com.ezddd.common.annotation.EzComponent;
import com.ezddd.common.command.AbstractCommandBus;
import com.ezddd.common.command.Command;
import com.ezddd.common.command.CommandHandler;
import com.ezddd.common.response.AppResult;

@EzComponent
public class DefaultCommandBus extends AbstractCommandBus {

    @Override
    public AppResult dispatch(Command cmd) {
        CommandHandler<AppResult> commandHandler = commandRegistry.findCommandExecutor(cmd);
        if (commandHandler == null) {
            return AppResult.valueOfError("001");
        } else {
            return commandHandler.execute(cmd);
        }
    }

}
