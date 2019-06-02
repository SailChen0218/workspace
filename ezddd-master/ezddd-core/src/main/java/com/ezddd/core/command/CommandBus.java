package com.ezddd.core.command;

import com.ezddd.core.response.CommandResult;

public interface CommandBus {
    CommandResult<?> dispatch(Command cmd);
}
