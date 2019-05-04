package com.ezddd.core.command;

import com.ezddd.core.response.AppResult;

public interface CommandHandler<R> {
    <R> AppResult<R> execute(Command cmd);
}
