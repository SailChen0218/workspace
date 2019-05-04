package com.ezddd.core.appservice;


import com.ezddd.core.annotation.EzRemoting;
import com.ezddd.core.command.Command;
import com.ezddd.core.command.CommandGateway;
import com.ezddd.core.response.AppResult;

public abstract class AbstractAppService implements AppService {

    @EzRemoting
    protected CommandGateway commandGateway;

    @Override
    public <R> AppResult<R> send(Command command) {
        return commandGateway.send(command);
    }
}
