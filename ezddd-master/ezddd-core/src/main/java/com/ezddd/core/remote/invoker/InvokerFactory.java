package com.ezddd.core.remote.invoker;

import com.ezddd.core.remote.protocol.ProtocolType;

public class InvokerFactory {

    private InvokerFactory() {
    }

    public static Invoker create(String url, String protocolType) throws CreateInvokerFailedException {
        try
        {
            switch (protocolType) {
                case ProtocolType.HESSIAN:
                    return new HessainInvoker(url);
            }
            throw new CreateInvokerFailedException("ProtocolType not found.");
        } catch (Exception e) {
            throw new CreateInvokerFailedException(e.getMessage(), e);
        }
    }
}
