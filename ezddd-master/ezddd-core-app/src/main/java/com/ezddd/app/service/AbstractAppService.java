package com.ezddd.app.service;


import com.ezddd.common.annotation.EzRemoting;
import com.ezddd.common.command.Command;
import com.ezddd.common.command.CommandGateway;
import com.ezddd.common.response.AppResult;

public abstract class AbstractAppService implements AppService {

    @EzRemoting
    protected CommandGateway commandGateway;

    @Override
    public <R> AppResult<R> send(Command command) {
        return commandGateway.send(command);
    }
}
