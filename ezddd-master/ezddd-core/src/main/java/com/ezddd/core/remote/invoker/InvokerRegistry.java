package com.ezddd.core.remote.invoker;

import com.ezddd.core.remote.protocol.ProtocolType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class InvokerRegistry {
    private static final Logger log = LoggerFactory.getLogger(InvokerRegistry.class);
    /**
     * Map of *Protocol, Invoker*
     */
    private static final Map<String, Invoker> invokerHolder = new HashMap<>();

    static {
        try {
            invokerHolder.put(ProtocolType.HESSIAN, new HessainInvoker());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    public static Invoker findeInvoker(String protocol) {
        return invokerHolder.get(protocol);
    }
}
