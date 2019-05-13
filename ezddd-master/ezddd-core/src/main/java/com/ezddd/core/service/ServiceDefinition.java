package com.ezddd.core.service;

import com.ezddd.core.annotation.EzDomainService;
import org.springframework.util.Assert;

public class ServiceDefinition {
    private Class<?> interfaceType;
    private String protocol;
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
            serviceDefinition.protocol = ezDomainService.protocol();
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
}
