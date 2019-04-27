package com.ezddd.app.command;


import com.ezddd.common.response.AppResult;

public interface CommandExecutor<R> {
    <R> AppResult<R> execute(Command cmd);
}
