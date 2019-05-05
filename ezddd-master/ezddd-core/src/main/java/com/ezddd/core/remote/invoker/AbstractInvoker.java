package com.ezddd.core.remote.invoker;

import com.ezddd.core.remote.receiver.IRemoteCallReceiver;
import com.ezddd.core.response.RpcResult;

public class AbstractInvoker implements Invoker {
    protected IRemoteCallReceiver remoteCallReceiver;

    public AbstractInvoker() {
    }

    public AbstractInvoker(IRemoteCallReceiver remoteCallReceiver) {
        this.remoteCallReceiver = remoteCallReceiver;
    }

    @Override
    public <T> RpcResult<T> invoke(Invocation invocation) {
        return remoteCallReceiver.receive(invocation);
    }
}
