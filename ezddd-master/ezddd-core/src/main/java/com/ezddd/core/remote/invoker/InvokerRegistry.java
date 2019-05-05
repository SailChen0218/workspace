package com.ezddd.core.remote.invoker;

import com.ezddd.core.remote.RpcType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class InvokerRegistry {
    private static final Logger log = LoggerFactory.getLogger(InvokerRegistry.class);
    private static final Map<RpcType, Invoker> invokerHolder = new HashMap<>();

    static {
        try {
            invokerHolder.put(RpcType.HESSIAN, new HessainInvoker());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static Invoker findeInvoker(RpcType rpcType) {
        return invokerHolder.get(rpcType);
    }
}
