package com.ezddd.core.command;

public class CommandExecutionFailedException extends RuntimeException {
    public CommandExecutionFailedException(String message) {
    }

    public CommandExecutionFailedException(Throwable cause) {
        super(cause);
    }
}
