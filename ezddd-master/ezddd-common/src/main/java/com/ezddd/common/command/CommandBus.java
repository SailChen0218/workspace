package com.ezddd.common.command;

public interface CommandBus<R> {
    <R> R dispatch(Command cmd);
}
