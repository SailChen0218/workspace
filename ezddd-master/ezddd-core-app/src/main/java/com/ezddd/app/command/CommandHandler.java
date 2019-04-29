package com.ezddd.app.command;


import com.ezddd.common.response.AppResult;

public interface CommandHandler<R> {
    <R> AppResult<R> execute(Command cmd);
}
