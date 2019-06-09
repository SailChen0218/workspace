package com.ezddd.core.remote;

import com.ezddd.core.annotation.EzRemoting;
import com.ezddd.core.remote.invoker.Invoker;
import com.ezddd.core.remote.invoker.InvokerFactory;

import java.lang.reflect.Proxy;

public class RemoteProxyFactory {

    public static <T> T create(Class<T> clazz, String protocolType) {
//        String domain = "order-domain";
        String url = "http://localhost:8081/order-domain/hessian.do";
//        EzRemoting ezRemoting = clazz.getAnnotation(EzRemoting.class);
//        if (ezRemoting != null && !"".equals(ezRemoting.domain())) {
//            domain = ezRemoting.domain();
//        }
        Invoker invoker = InvokerFactory.create(url, protocolType);
        Class<?>[] interfaces = new Class<?>[]{clazz};
        return (T) Proxy.newProxyInstance(clazz.getClassLoader(), interfaces,
                new RemoteProxyIntercepter(clazz, invoker));
    }
}
