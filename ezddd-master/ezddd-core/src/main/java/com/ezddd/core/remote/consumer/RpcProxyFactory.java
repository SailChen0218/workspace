package com.ezddd.core.remote.consumer;

public interface RpcProxyFactory {
    <T> T create(Class<T> clazz);
}
