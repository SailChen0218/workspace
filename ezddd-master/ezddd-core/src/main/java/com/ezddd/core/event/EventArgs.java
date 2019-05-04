package com.ezddd.core.event;

public class EventArgs<T> {
    private T args;
    public EventArgs(T args) {
        this.args = args;
    }
    public <T> T getArgs() {
        return (T)this.args;
    }
}
