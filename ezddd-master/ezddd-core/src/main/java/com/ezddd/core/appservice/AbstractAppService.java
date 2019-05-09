package com.ezddd.core.appservice;

import com.ezddd.core.annotation.EzRemoting;
import com.ezddd.core.command.Command;
import com.ezddd.core.command.CommandGateway;
import com.ezddd.core.response.AppResult;
import com.ezddd.core.response.CommandResult;

public abstract class AbstractAppService implements AppService {

    @EzRemoting
    protected CommandGateway commandGateway;

    @Override
    public <T> AppResult<T> send(Command command) {
        CommandResult<T> commandResult = commandGateway.send(command);
        if (commandResult.hasException()) {
            return AppResult.valueOfError("001", commandResult.getException().getMessage());
        } else {
            return AppResult.valueOfSuccess(commandResult.getValue());
        }
    }
}
