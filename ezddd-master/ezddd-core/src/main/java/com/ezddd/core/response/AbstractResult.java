package com.ezddd.core.response;

public class AbstractResult<T> implements Result<T> {
    private static final long serialVersionUID = 3898146277943695141L;

    protected T value;

    protected Throwable exception;

    @Override
    public T getValue() {
        return value;
    }

    public void setValue(T value) {
        this.value = value;
    }

    @Override
    public Throwable getException() {
        return exception;
    }

    public void setException(Throwable exception) {
        this.exception = exception;
    }

    @Override
    public boolean hasException() {
        return false;
    }
}
