package com.ezddd.common.remote;

import java.util.HashMap;
import java.util.Map;

public class RemoteProxyFactoryRegistry {
    private static final Map<RpcType, RemoteProxyFactory> remoteProxyFactoryHolder = new HashMap<>();

    static {
        remoteProxyFactoryHolder.put(RpcType.HESSIAN, new HessionRemoteProxyFactory());
    }

    public static <T> T create(RpcType rpcType, Class<T> clazz) {
        return remoteProxyFactoryHolder.get(rpcType).create(clazz);
    }
}
