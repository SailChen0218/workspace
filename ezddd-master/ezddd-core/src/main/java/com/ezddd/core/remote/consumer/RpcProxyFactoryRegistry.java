package com.ezddd.core.remote.consumer;

import com.ezddd.core.remote.RpcType;

import java.util.HashMap;
import java.util.Map;

public class RpcProxyFactoryRegistry {
    private static final Map<RpcType, RpcProxyFactory> remoteProxyFactoryHolder = new HashMap<>();

    static {
        remoteProxyFactoryHolder.put(RpcType.HESSIAN, new HessionRpcProxyFactory());
    }

    public static RpcProxyFactory findeRemoteProxyFactory(RpcType rpcType) {
        return remoteProxyFactoryHolder.get(rpcType);
    }
}
