package com.ezddd.core.command;

import com.ezddd.core.response.CommandResult;

public interface CommandGateway {
    CommandResult<?> send(Command command);
}
