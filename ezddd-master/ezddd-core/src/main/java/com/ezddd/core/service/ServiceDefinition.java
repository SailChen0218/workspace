package com.ezddd.core.service;

import com.ezddd.core.annotation.EzDomainService;
import org.springframework.util.Assert;

public class ServiceDefinition {
    private Class<?> interfaceType;
    private String protocol;
    private Object serviceBean;
    private Class<?> serviceType;
    private ServiceDefinition() {
    }

    public static ServiceDefinition build(Class<?> serviceType) {
        Assert.notNull(serviceType, "parameter serviceBean must not be null.");
        EzDomainService ezDomainService = serviceType.getAnnotation(EzDomainService.class);
        if (ezDomainService != null) {
            ServiceDefinition serviceDefinition = new ServiceDefinition();
            serviceDefinition.interfaceType = ezDomainService.interfaceType();
            serviceDefinition.protocol = ezDomainService.protocol();
            serviceDefinition.serviceType = serviceType;
            return serviceDefinition;
        } else {
            throw new IllegalArgumentException("EzDomainService annotation not found. bean:" + serviceType.getName());
        }
    }

    public Class<?> getInterfaceType() {
        return interfaceType;
    }

    public void setInterfaceType(Class<?> interfaceType) {
        this.interfaceType = interfaceType;
    }

    public String getProtocol() {
        return protocol;
    }

    public void setProtocol(String protocol) {
        this.protocol = protocol;
    }

    public Object getServiceBean() {
        return serviceBean;
    }

    public void setServiceBean(Object serviceBean) {
        this.serviceBean = serviceBean;
    }

    public Class<?> getServiceType() {
        return serviceType;
    }

    public void setServiceType(Class<?> serviceType) {
        this.serviceType = serviceType;
    }
}
