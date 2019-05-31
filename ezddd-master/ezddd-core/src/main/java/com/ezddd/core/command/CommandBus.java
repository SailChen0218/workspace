package com.ezddd.core.command;

import com.ezddd.core.response.CommandResult;

public interface CommandBus<T> {
    CommandResult<T> dispatch(Command cmd);
}
