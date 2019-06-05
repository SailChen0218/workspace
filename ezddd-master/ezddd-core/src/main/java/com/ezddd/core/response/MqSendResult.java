package com.ezddd.core.response;

import java.io.Serializable;

public class MqSendResult<T> implements Serializable {
    private static final long serialVersionUID = -4323366786484007045L;

    private T value;
    private boolean success;
    private String exceptionContent;

    public static <T> MqSendResult<T> valueOfSuccess() {
        MqSendResult<T> result = new MqSendResult<>();
        result.success = true;
        return result;
    }

    public static <T> MqSendResult<T> valueOfSuccess(T value) {
        MqSendResult<T> result = new MqSendResult<>();
        result.value = value;
        result.success = true;
        return result;
    }

    public static <T> MqSendResult<T> valueOfError(Throwable exception) {
        MqSendResult<T> result = new MqSendResult<>();
        result.success = false;
        result.exceptionContent = exception.getMessage();
        return result;
    }

    public static <T> MqSendResult<T> valueOfError(String message) {
        MqSendResult<T> result = new MqSendResult<>();
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
