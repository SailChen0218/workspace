package com.ezddd.core.response;

import java.io.Serializable;

public class CommandResult<T> implements Serializable {
    private static final long serialVersionUID = -4323366786484007045L;

    private T value;
    private boolean success;
    private String exceptionContent;

    public static <T> CommandResult<T> valueOfSuccess() {
        CommandResult<T> result = new CommandResult<>();
        return result;
    }

    public static <T> CommandResult<T> valueOfSuccess(T value) {
        CommandResult<T> result = new CommandResult<>();
        result.value = value;
        result.success = true;
        return result;
    }

    public static <T> CommandResult<T> valueOfError(Throwable exception) {
        CommandResult<T> result = new CommandResult<>();
        result.success = false;
        result.exceptionContent = exception.getMessage();
        return result;
    }

    public static <T> CommandResult<T> valueOfError(String message) {
        CommandResult<T> result = new CommandResult<>();
        result.success = false;
        result.exceptionContent = message;
        return result;
    }

    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getExceptionContent() {
        return exceptionContent;
    }

    public void setExceptionContent(String exceptionContent) {
        this.exceptionContent = exceptionContent;
    }
}
