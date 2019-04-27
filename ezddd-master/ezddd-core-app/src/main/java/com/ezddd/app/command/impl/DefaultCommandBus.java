package com.ezddd.app.command.impl;

import com.ezddd.app.command.AbstractCommandBus;
import com.ezddd.app.command.Command;
import com.ezddd.app.command.CommandExecutor;
import com.ezddd.common.annotation.EzComponent;
import com.ezddd.common.response.AppResult;


@EzComponent
public class DefaultCommandBus extends AbstractCommandBus {

    @Override
    public AppResult dispatch(Command cmd) {
        CommandExecutor<AppResult> commandExecutor = commandExecutorRegistry.findCommandExecutor(cmd);
        if (commandExecutor == null) {
            return AppResult.valueOfError("001");
        } else {
            return commandExecutor.execute(cmd);
        }
    }

}
