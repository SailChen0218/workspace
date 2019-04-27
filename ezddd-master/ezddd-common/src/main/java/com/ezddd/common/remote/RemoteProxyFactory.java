package com.ezddd.common.remote;

public interface RemoteProxyFactory {
    <T> T create(Class<T> clazz);
}
