package com.ezddd.core.service;

import com.ezddd.core.annotation.EzDomainService;
import com.ezddd.core.remote.protocol.Protocol;
import org.springframework.util.Assert;

public class ServiceDefinition {
    private Class<?> interfaceType;
    private Protocol.Type protocolType;
    private Object serviceBean;

    private ServiceDefinition() {
    }

    public static ServiceDefinition build(Object serviceBean) {
        Assert.notNull(serviceBean, "parameter serviceBean must not be null.");
        Class<?> serviceBeanClazz = serviceBean.getClass();
        EzDomainService ezDomainService = serviceBeanClazz.getAnnotation(EzDomainService.class);
        if (ezDomainService != null) {
            ServiceDefinition serviceDefinition = new ServiceDefinition();
            serviceDefinition.interfaceType = ezDomainService.interfaceType();
            serviceDefinition.rpcType = ezDomainService.rpcType();
            serviceDefinition.serviceBean = serviceBean;
            return serviceDefinition;
        } else {
            throw new IllegalArgumentException("EzDomainService annotation not found. bean:" + serviceBeanClazz.getName());
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
