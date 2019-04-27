package com.ezddd.app.service;

import com.ezddd.app.command.Command;
import com.ezddd.app.command.CommandGateway;
import com.ezddd.common.response.AppResult;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractAppService implements AppService {

    @Autowired
    protected CommandGateway commandGateway;

    @Override
    public <R> AppResult<R> send(Command command) {
        return commandGateway.send(command);
    }
}
