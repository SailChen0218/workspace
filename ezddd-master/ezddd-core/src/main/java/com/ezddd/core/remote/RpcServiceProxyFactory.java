package com.ezddd.core.remote;

import com.ezddd.core.remote.invoker.Invoker;
import com.ezddd.core.remote.invoker.InvokerRegistry;
import org.springframework.cglib.proxy.Enhancer;

public class RpcServiceProxyFactory {
    public static <T> T create(Class<T> clazz, RpcType rpcType) {
        Invoker invoker = InvokerRegistry.findeInvoker(rpcType);
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(new RpcServiceProxyIntercepter(invoker));
        return (T)enhancer.create();
    }
}
