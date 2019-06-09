package com.ezddd.core.remote.invoker;

import com.caucho.hessian.client.HessianProxyFactory;
import com.ezddd.core.annotation.EzCommandMapping;
import com.ezddd.core.remote.receiver.IRemoteCallReceiver;

import java.net.MalformedURLException;

public class HessainInvoker extends AbstractInvoker {
    private static class HessianProxyFactorySingleton {
        static final HessianProxyFactory instance = new HessianProxyFactory();

        static {
            instance.setConnectTimeout(30 * 1000);
            instance.setReadTimeout(30 * 1000);
            instance.setOverloadEnabled(true);
        }
    }

    public HessainInvoker(String url) throws MalformedURLException {
        remoteCallReceiver = (IRemoteCallReceiver) HessianProxyFactorySingleton.instance
                .create(IRemoteCallReceiver.class, url);
    }
}
