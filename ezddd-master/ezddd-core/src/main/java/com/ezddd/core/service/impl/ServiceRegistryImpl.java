package com.ezddd.core.service.impl;

import com.ezddd.core.annotation.EzAggregate;
import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.annotation.EzService;
import com.ezddd.core.service.ServiceDefinition;
import com.ezddd.core.service.ServiceRegistry;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;

import java.util.HashMap;
import java.util.Map;

@EzComponent
public class ServiceRegistryImpl implements ServiceRegistry {
    protected static Map<String, ServiceDefinition> serviceDefinitionHolder = new HashMap<>(16);

    @Override
    public void registry(BeanFactory beanFactory) {
        ConfigurableListableBeanFactory listableBeanFactory = (ConfigurableListableBeanFactory) beanFactory;
        String[] beanNames = listableBeanFactory.getBeanNamesForAnnotation(EzService.class);
        if (beanNames != null && beanNames.length > 0) {
            for (int i = 0; i < beanNames.length; i++) {
                Object bean = listableBeanFactory.getBean(beanNames[i]);
                ServiceDefinition serviceDefinition = ServiceDefinition.build(bean);
                serviceDefinitionHolder.put(serviceDefinition.getInterfaceType().getName(), serviceDefinition);
            }
        }
    }

    @Override
    public ServiceDefinition findeServiceDefinition(String interfaceName) {
        return serviceDefinitionHolder.get(interfaceName);
    }

}
