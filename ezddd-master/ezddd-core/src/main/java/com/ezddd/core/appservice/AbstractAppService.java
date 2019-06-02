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
    public CommandResult<?> send(Command command) {
        return commandGateway.send(command);
    }

    public AppResult<?> send(Command command, String successCode, String errorCode, Object[] params) {
        CommandResult result = send(command);
        if (!result.isSuccess()) {
            return AppResult.valueOfError(result.getExceptionContent(), errorCode, params);
        } else {
            return AppResult.valueOfSuccess(result.getValue(), successCode, params);
        }
    }
}
