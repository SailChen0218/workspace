package com.ezddd.core.remote.invoker;

import com.caucho.hessian.client.HessianProxyFactory;
import com.ezddd.core.remote.receiver.IRemoteCallReceiver;

public class HessainInvoker extends AbstractInvoker {
    private static String HESSION_URI = "http://localhost:8081/order/domain/hessian.do";

    public HessainInvoker() throws Exception {
        HessianProxyFactory hessianProxyFactory = new HessianProxyFactory();
        hessianProxyFactory.setOverloadEnabled(true);
        remoteCallReceiver = (IRemoteCallReceiver)hessianProxyFactory.create(IRemoteCallReceiver.class, HESSION_URI);
    }
}
