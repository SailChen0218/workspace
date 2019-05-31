package com.ezddd.core.command;

import com.ezddd.core.response.CommandResult;

public interface CommandGateway {
    <T> CommandResult<T> send(Command command);
}
