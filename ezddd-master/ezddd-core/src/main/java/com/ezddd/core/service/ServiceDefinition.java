package com.ezddd.core.service;

import com.ezddd.core.annotation.EzService;
import com.ezddd.core.remote.RpcType;
import org.springframework.util.Assert;

public class ServiceDefinition {
    private Class<?> interfaceType;
    private RpcType rpcType;
    private Object serviceBean;

    public static ServiceDefinition build(Object serviceBean) {
        Assert.notNull(serviceBean, "parameter serviceBean must not be null.");
        Class<?> serviceBeanClazz = serviceBean.getClass();
        EzService ezService = serviceBeanClazz.getAnnotation(EzService.class);
        if (ezService != null) {
            ServiceDefinition serviceDefinition = new ServiceDefinition();
            serviceDefinition.interfaceType = ezService.interfaceType();
            serviceDefinition.rpcType = ezService.rpcType();
            return serviceDefinition;
        } else {
            throw new IllegalArgumentException("EzService annotation not found. bean:" + serviceBeanClazz.getName());
        }
    }

    public Class<?> getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(Class<?> interfaceType) {
        this.interfaceType = interfaceType;
    }

    public RpcType getRpcType() {
        return rpcType;
    }

    public void setRpcType(RpcType rpcType) {
        this.rpcType = rpcType;
    }

    public Object getServiceBean() {
        return serviceBean;
    }

    public void setServiceBean(Object serviceBean) {
        this.serviceBean = serviceBean;
    }
}
