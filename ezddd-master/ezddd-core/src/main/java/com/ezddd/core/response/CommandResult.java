package com.ezddd.core.response;

public class CommandResult<T> extends AbstractResult<T> {

    public static <T> CommandResult<T> valueOfSuccess() {
        CommandResult<T> result = new CommandResult<>();
        return result;
    }

    public static <T> CommandResult<T> valueOfSuccess(T value) {
        CommandResult<T> result = new CommandResult<>();
        result.value = value;
        return result;
    }

    public static <T> CommandResult<T> valueOfError(Throwable throwable) {
        CommandResult<T> result = new CommandResult<>();
        result.setException(throwable);
        return result;
    }

}
