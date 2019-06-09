package com.ezddd.core.command.query.constraints;

public interface Validator<T> {
    boolean validate(T value) throws ValidatorException;
}
