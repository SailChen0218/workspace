package com.ezddd.core.command;

import com.ezddd.core.response.CommandResult;

public interface CommandHandler {
    <T> CommandResult<T> handle(Command command);
}
