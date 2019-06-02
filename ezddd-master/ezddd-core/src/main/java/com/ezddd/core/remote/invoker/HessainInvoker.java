package com.ezddd.core.remote.invoker;

import com.caucho.hessian.client.HessianProxyFactory;
import com.ezddd.core.remote.receiver.IRemoteCallReceiver;

public class HessainInvoker extends AbstractInvoker {
    private static String HESSION_URI = "http://localhost:8081/order/domain/hessian.do";
    private static class HessianProxyFactorySingleton {
        static final HessianProxyFactory instance = new HessianProxyFactory();
        static {
            instance.setOverloadEnabled(true);
        }
    }
    public HessainInvoker() throws Exception {
        remoteCallReceiver = (IRemoteCallReceiver)HessianProxyFactorySingleton.instance
                .create(IRemoteCallReceiver.class, HESSION_URI);
    }
}
