package com.ezddd.core.command;

@FunctionalInterface
public interface CommandCallback<T> {
    void onResult(T result);
}
