package com.ezddd.core.remote;

import com.ezddd.core.remote.invoker.Invoker;
import com.ezddd.core.remote.invoker.InvokerRegistry;
import org.springframework.cglib.proxy.Enhancer;

import java.lang.reflect.Proxy;

public class RpcServiceProxyFactory {
//    public static <T> T create(Class<T> clazz, RpcType rpcType) {
//        Invoker invoker = InvokerRegistry.findeInvoker(rpcType);
//        Enhancer enhancer = new Enhancer();
//        enhancer.setSuperclass(clazz);
//        enhancer.setCallback(new RpcServiceProxyIntercepter(clazz, invoker));
//        return (T)enhancer.create();
//    }
    public static <T> T create(Class<T> clazz, RpcType rpcType) {
        Invoker invoker = InvokerRegistry.findeInvoker(rpcType);
        Class<?>[] interfaces = new Class<?>[]{clazz};
        return (T)Proxy.newProxyInstance(clazz.getClassLoader(), interfaces,
                new RpcServiceProxyIntercepter(clazz, invoker));
    }
}
