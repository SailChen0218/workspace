package com.ezddd.core.command;

public interface CommandBus<R> {
    <R> R dispatch(Command cmd);
}
