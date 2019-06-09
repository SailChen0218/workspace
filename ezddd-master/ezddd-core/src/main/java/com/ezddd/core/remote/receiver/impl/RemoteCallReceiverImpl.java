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
import org.springframework.util.Assert;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@EzComponent
public class RemoteCallReceiverImpl implements IRemoteCallReceiver {
    private static final Logger log = LoggerFactory.getLogger(RemoteCallReceiverImpl.class);

    @Autowired
    ServiceRegistry serviceRegistry;

    @Override
    public <T> RpcResult<T> receive(Invocation invocation) {
        ServiceDefinition serviceDefinition = serviceRegistry.findeServiceDefinition(invocation.getInterfaceName());
        Assert.notNull(serviceDefinition, "cannot found serviceDefinition of bean:"
                + invocation.getInterfaceName());
        Object serviceBean = serviceDefinition.getServiceBean();
        try {
            Object[] arguments = invocation.getArguments();
            Class<?>[] parameterTypes = invocation.getParameterTypes();
            Object[] argumentsForInvoke = null;
            if (arguments != null) {
                argumentsForInvoke = new Object[arguments.length];
                for (int i = 0; i < arguments.length; i++) {
                    argumentsForInvoke[i] = parameterTypes[i].cast(arguments[i]);
                }
            }
            Method method = serviceDefinition.getInterfaceType().getMethod(invocation.getMethodName(), parameterTypes);
            Object result = method.invoke(serviceBean, argumentsForInvoke);
            return RpcResult.valueOfSuccess((T)result);
        } catch (NoSuchMethodException e) {
            log.error("NoSuchMethodException occurred when invoke " + invocation.getInterfaceName(), e);
            return RpcResult.valueOfError(e.getCause());
        } catch (IllegalAccessException e) {
            log.error("IllegalAccessException occurred when invoke " + invocation.getInterfaceName(), e);
            return RpcResult.valueOfError(e.getCause());
        } catch (InvocationTargetException e) {
            log.error("InvocationTargetException occurred when invoke " + invocation.getInterfaceName(), e);
            return RpcResult.valueOfError(e.getTargetException());
        }
    }

    private Method findMethod(Class<?> targetClazz, String methodName,
                              Class<?>[] parameterTypes) throws NoSuchMethodException {
        try {
            return targetClazz.getMethod(methodName, parameterTypes);
        } catch (NoSuchMethodException e) {
            Method[] methods = targetClazz.getMethods();
            boolean isParameterMatched = true;
            for (int i = 0; i < methods.length; i++) {
                if (methodName.equals(methods[i])) {
                    Class<?>[] types = methods[i].getParameterTypes();
                    if (types.length == parameterTypes.length) {
                        for (int j = 0; j < parameterTypes.length; j++) {
                            if (!parameterTypes[j].isAssignableFrom(types[j])) {
                                isParameterMatched = false;
                                break;
                            }
                        }
                        if (isParameterMatched) {
                            return methods[i];
                        }
                    }
                }
            }
            throw e;
        }
    }

}
