package com.ezddd.core.command;

public abstract class AbstractCommand implements Command {
    protected String bizCode;
    protected String bizDetailCode;
    public String getBizCode() {
        return bizCode;
    }
    public String getBizDetailCode() {
        return bizDetailCode;
    }
}