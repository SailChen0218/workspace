package com.ezddd.core.response;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

public class QueryResult<T> implements Serializable {
    private static final long serialVersionUID = -4323366786484007045L;

    private T value;
    private boolean success;
    private String exceptionContent;
    private boolean isEmpty;
    private int count;

    public static <T> QueryResult<T> valueOfSuccess(T value) {
        QueryResult<T> result = new QueryResult<>();
        result.value = value;
        result.success = true;
        if (value == null) {
            result.isEmpty = true;
        } else {
            Class<?> valueType = value.getClass();
            if (Collection.class.isAssignableFrom(valueType)) {
                Collection collection = (Collection) value;
                result.isEmpty = collection.isEmpty();
                result.count = collection.size();
            } else {
                result.isEmpty = false;
                result.count = 1;
            }

            if (Map.class.isAssignableFrom(valueType)) {
                throw new IllegalArgumentException("Query results cannot be map type.");
            }
        }
        return result;
    }

    public static <T> QueryResult<T> valueOfError(Throwable exception) {
        QueryResult<T> result = new QueryResult<>();
        result.success = false;
        result.exceptionContent = exception.getMessage();
        return result;
    }

    public static <T> QueryResult<T> valueOfError(String message) {
        QueryResult<T> result = new QueryResult<>();
        result.success = false;
        result.exceptionContent = message;
        return result;
    }

    public T getValue() {
        return value;
    }

    public boolean isEmpty() {
        return this.isEmpty;
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

    public int getCount() {
        return this.count;
    }
}
