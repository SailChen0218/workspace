package com.ezddd.core.appservice;

import com.ezddd.core.annotation.EzCommandMapping;
import com.ezddd.core.annotation.EzQueryMapping;

import java.lang.reflect.Method;

public class MethodDefiniton {
    private Method method;
    private int methodType;

    public static final int COMMAND = 1;
    public static final int QUERY = 2;

    public MethodDefiniton(Method method) {
        this.method = method;
        this.methodType = getMethodType(method);
    }

    public Method getMethod() {
        return method;
    }

    public void setMethod(Method method) {
        this.method = method;
    }

    public int getMethodType() {
        return methodType;
    }

    public void setMethodType(int methodType) {
        this.methodType = methodType;
    }

    private int getMethodType(Method method) {
        EzCommandMapping commandMapping = method.getAnnotation(EzCommandMapping.class);
        if (commandMapping != null) {
            return MethodDefiniton.COMMAND;
        } else {
            EzQueryMapping queryMapping = method.getAnnotation(EzQueryMapping.class);
            if (queryMapping != null) {
                return MethodDefiniton.QUERY;
            }
        }
        return 0;
    }
}
