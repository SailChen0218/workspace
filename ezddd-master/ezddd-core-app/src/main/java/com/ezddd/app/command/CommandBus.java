package com.ezddd.app.command;

public interface CommandBus<R> {
    <R> R dispatch(Command cmd);
}
