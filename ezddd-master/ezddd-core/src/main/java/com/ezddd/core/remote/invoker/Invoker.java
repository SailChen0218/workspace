package com.ezddd.core.remote.invoker;

import com.ezddd.core.response.RpcResult;

public interface Invoker {
    <T> RpcResult<T> invoke(Invocation invocation);
}
