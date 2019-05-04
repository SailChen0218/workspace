package com.ezddd.core.command.impl;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.command.AbstractCommandBus;
import com.ezddd.core.command.Command;
import com.ezddd.core.response.AppResult;

@EzComponent
public class AsynchronousCommandBus extends AbstractCommandBus {

    @Override
    public AppResult dispatch(Command cmd) {
//        CommandHandler<AppResult> commandHandler = commandRegistry.findCommandExecutor(cmd);
//        if (commandHandler == null) {
//            return AppResult.valueOfError("001");
//        } else {
//            return commandHandler.execute(cmd);
//        }
        return AppResult.valueOfSuccess();
    }
}
