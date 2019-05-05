package com.ezddd.core.remote.receiver;

import com.ezddd.core.remote.invoker.Invocation;
import com.ezddd.core.response.RpcResult;

public interface IRemoteCallReceiver {
    <T> RpcResult<T> receive(Invocation invocation);
}
