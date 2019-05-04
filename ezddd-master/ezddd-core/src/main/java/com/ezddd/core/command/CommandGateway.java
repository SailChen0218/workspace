package com.ezddd.core.command;

import com.ezddd.core.response.AppResult;

public interface CommandGateway<R> {
    <R> AppResult<R> send(Command command);
}
