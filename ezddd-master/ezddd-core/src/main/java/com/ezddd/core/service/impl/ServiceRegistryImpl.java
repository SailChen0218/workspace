package com.ezddd.core.service.impl;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.annotation.EzDomainService;
import com.ezddd.core.service.ServiceDefinition;
import com.ezddd.core.service.ServiceRegistry;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import java.util.HashMap;
import java.util.Map;

@EzComponent
public class ServiceRegistryImpl implements ServiceRegistry {
    protected static Map<String, ServiceDefinition> serviceDefinitionHolder = new HashMap<>(16);
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void registry(BeanFactory beanFactory) {
        this.beanFactory = (ConfigurableListableBeanFactory) beanFactory;
        String[] beanNames = this.beanFactory.getBeanNamesForAnnotation(EzDomainService.class);
        if (beanNames != null && beanNames.length > 0) {
            GenericBeanDefinition beanDefinition = null;
            for (int i = 0; i < beanNames.length; i++) {
                beanDefinition = (GenericBeanDefinition)this.beanFactory.getBeanDefinition(beanNames[i]);
                ServiceDefinition serviceDefinition = ServiceDefinition.build(beanDefinition.getBeanClass());
                serviceDefinitionHolder.put(serviceDefinition.getInterfaceType().getName(), serviceDefinition);
            }
        }
    }

    @Override
    public ServiceDefinition findeServiceDefinition(String interfaceName) {
        if (serviceDefinitionHolder.containsKey(interfaceName)) {
            ServiceDefinition serviceDefinition = serviceDefinitionHolder.get(interfaceName);
            if (serviceDefinition.getServiceBean() == null) {
                Object serviceBean = this.beanFactory.getBean(serviceDefinition.getServiceType());
                serviceDefinition.setServiceBean(serviceBean);
            }
            return serviceDefinition;
        } else {
            throw new IllegalArgumentException(
                    "There is no ServiceDefinition of interface:" + interfaceName + " exists. ");
        }
    }

}
