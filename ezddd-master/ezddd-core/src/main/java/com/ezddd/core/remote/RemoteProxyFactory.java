package com.ezddd.core.remote;

import com.ezddd.core.remote.invoker.Invoker;
import com.ezddd.core.remote.invoker.InvokerRegistry;

import java.lang.reflect.Proxy;

public class RemoteProxyFactory {
    public static <T> T create(Class<T> clazz, String protocol) {
        Invoker invoker = InvokerRegistry.findeInvoker(protocol);
        Class<?>[] interfaces = new Class<?>[]{clazz};
        return (T)Proxy.newProxyInstance(clazz.getClassLoader(), interfaces,
                new RemoteProxyIntercepter(clazz, invoker));
    }
}
