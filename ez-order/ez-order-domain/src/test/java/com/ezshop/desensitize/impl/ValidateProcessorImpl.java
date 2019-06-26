package com.ezshop.desensitize.impl;

import com.ezshop.desensitize.ValidateProcessor;
import org.springframework.stereotype.Component;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.ValidatorFactory;
import javax.validation.executable.ExecutableValidator;
import java.lang.reflect.Method;
import java.util.Set;

@Component
public class ValidateProcessorImpl implements ValidateProcessor {
    private static class ValidatorSingleton {
        private final ValidatorFactory factory;
        private final ExecutableValidator executableValidator;

        private ValidatorSingleton() {
            this.factory = Validation.buildDefaultValidatorFactory();
            this.executableValidator = factory.getValidator().forExecutables();
        }

        private static final ValidatorSingleton instance = new ValidatorSingleton();
    }

    private static ExecutableValidator getExecutableValidator() {
        return ValidatorSingleton.instance.executableValidator;
    }

    @Override
    public Set<ConstraintViolation<Object>> validateParameters(Object object, Method method, Object[] parameterValues) {
        return getExecutableValidator().validateParameters(object, method, parameterValues);
    }
}
