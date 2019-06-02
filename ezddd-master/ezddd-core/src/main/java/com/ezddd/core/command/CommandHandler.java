package com.ezddd.core.command;

import com.ezddd.core.response.CommandResult;

public interface CommandHandler {
    CommandResult<?> handle(Command command);
}
