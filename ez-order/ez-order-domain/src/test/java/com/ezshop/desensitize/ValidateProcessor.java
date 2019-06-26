package com.ezshop.desensitize;

import javax.validation.ConstraintViolation;
import java.lang.reflect.Method;
import java.util.Set;

public interface ValidateProcessor {
    /**
     * 验证方法参数接口
     *
     * @param object
     * @param method
     * @param parameterValues
     * @return
     */
    Set<ConstraintViolation<Object>> validateParameters(Object object, Method method, Object[] parameterValues);
}
