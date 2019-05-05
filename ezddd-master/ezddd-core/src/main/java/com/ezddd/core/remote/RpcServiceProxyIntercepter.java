package com.ezddd.core.remote;

import com.ezddd.core.remote.invoker.Invoker;
import com.ezddd.core.remote.invoker.RpcInvocation;
import com.ezddd.core.response.RpcResult;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

public class RpcServiceProxyIntercepter implements MethodInterceptor {
    private Invoker invoker;

    public RpcServiceProxyIntercepter(Invoker invoker) {
        this.invoker = invoker;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] args, MethodProxy methodProxy) throws Throwable {
        RpcInvocation invocation = new RpcInvocation();
        invocation.setInterfaceName(obj.getClass().getName());
        invocation.setMethodName(method.getName());
        invocation.setParameterTypes(method.getParameterTypes());
        invocation.setArguments(method.getParameters());
        RpcResult rpcResult = invoker.invoke(invocation);
        return rpcResult.getValue();
    }
}
