package com.ezddd.core.remote;

import com.ezddd.core.remote.invoker.Invoker;
import com.ezddd.core.remote.invoker.RpcInvocation;
import com.ezddd.core.response.RpcResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

public class RemoteProxyIntercepter implements InvocationHandler {
    private static final Logger log = LoggerFactory.getLogger(RemoteProxyIntercepter.class);
    private Invoker invoker;
    private Class<?> interfaceType;

    /**
     * equals方法
     */
    private Method equalsMethod;

    /**
     * toString方法
     */
    private Method toStringMethod;

    /**
     * hashCode方法
     */
    private Method hashCodeMethod;

    public RemoteProxyIntercepter(Class<?> interfaceType, Invoker invoker) {
        this.interfaceType = interfaceType;
        this.invoker = invoker;
    }

    /**
     * 获取方法名上加上参数类型签名 methodName(paramType0, ...)
     */
    private String convertMethodName(Method method) {
        StringBuilder name = new StringBuilder(method.getName());
        name.append("(");
        Class<?>[] parameterTypes = method.getParameterTypes();
        if (parameterTypes.length > 0) {
            name.append(parameterTypes[0].getName());
            for (int i = 1; i < parameterTypes.length; i++) {
                name.append(",").append(parameterTypes[i].getName());
            }
        }
        name.append(")");
        return name.toString();
    }

    private String getBeanName(Object obj) {
        String cglibClassName = obj.getClass().getName();
        return cglibClassName.split("\\$")[0];
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        String methodName = method.getName();
        if ("equals".equals(methodName)) {
            return null;
        }

        if ("toString".equals(methodName)) {
            return null;
        }

        if ("hashCode".equals(methodName)) {
            return null;
        }

        RpcInvocation invocation = new RpcInvocation();
        invocation.setInterfaceName(interfaceType.getName());
        invocation.setMethodName(methodName);
        invocation.setParameterTypes(method.getParameterTypes());
        invocation.setArguments(args);
        RpcResult rpcResult = invoker.invoke(invocation);
        return rpcResult.getValue();
    }
}
