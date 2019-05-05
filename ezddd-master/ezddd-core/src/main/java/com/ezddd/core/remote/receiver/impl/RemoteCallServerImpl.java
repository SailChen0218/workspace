package com.ezddd.core.remote.receiver.impl;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.remote.invoker.Invocation;
import com.ezddd.core.remote.receiver.IRemoteCallReceiver;
import com.ezddd.core.response.RpcResult;
import com.ezddd.core.service.ServiceDefinition;
import com.ezddd.core.service.ServiceRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.lang.reflect.Method;

@EzComponent
public class RemoteCallServerImpl implements IRemoteCallReceiver {
    private static final Logger log = LoggerFactory.getLogger(RemoteCallServerImpl.class);

    @Autowired
    ServiceRegistry serviceRegistry;

    @Override
    public <T> RpcResult<T> receive(Invocation invocation) {
        ServiceDefinition serviceDefinition = serviceRegistry.findeServiceDefinition(invocation.getInterfaceName());
        Object serviceBean = serviceDefinition.getServiceBean();
        try {
            Method method = serviceBean.getClass()
                    .getMethod(invocation.getMethodName(), invocation.getParameterTypes());
            Object result = method.invoke(serviceBean, invocation.getArguments());
            return RpcResult.valueOfSuccess((T)result);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return RpcResult.valueOfError(e);
        }
    }


}
