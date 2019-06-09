package com.ezddd.core.appservice.impl;

import com.ezddd.core.annotation.EzAppService;
import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.appservice.AppService;
import com.ezddd.core.appservice.AppServiceDefinition;
import com.ezddd.core.appservice.AppServiceRegistry;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@EzComponent
public class AppServiceRegistryImpl implements AppServiceRegistry {

    /**
     * Map of *bizCode, AppServiceDefinition*
     */
    protected static Map<String, AppServiceDefinition> appServiceDefinitionHolder = new HashMap<>(16);
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public AppServiceDefinition findAppServiceDefinition(String bizCode) {
        Assert.notNull(bizCode, "parameter bizCode must not be null");
        if (appServiceDefinitionHolder.containsKey(bizCode)) {
            AppServiceDefinition appServiceDefinition = appServiceDefinitionHolder.get(bizCode);
            if (appServiceDefinition.getAppService() == null) {
                AppService appService = (AppService) beanFactory.getBean(appServiceDefinition.getAppServiceType());
                appServiceDefinition.setAppService(appService);
            }
            return appServiceDefinition;
        } else {
            throw new IllegalArgumentException(
                    "There is no AppService definition of bizCode:" + bizCode + " exists. ");
        }
    }

    @Override
    public void registAppServiceDefinition(AppServiceDefinition appServiceDefinition) {
        Assert.notNull(appServiceDefinition, "parameter appServiceDefinition must not be null");
        if (appServiceDefinitionHolder.containsKey(appServiceDefinition.getBizCode())) {
            throw new IllegalArgumentException("bizCode already exists. " +
                    "appServie:" + appServiceDefinition.getAppServiceType().getName() +
                    ", bizCode:" + appServiceDefinition.getBizCode());
        }
        appServiceDefinitionHolder.put(appServiceDefinition.getBizCode(), appServiceDefinition);
    }

    @Override
    public void registry(BeanFactory beanFactory) {
        this.beanFactory = (ConfigurableListableBeanFactory)beanFactory;
        String[] beanNames = this.beanFactory.getBeanNamesForAnnotation(EzAppService.class);
        if (beanNames != null && beanNames.length > 0) {
            for (int i = 0; i < beanNames.length; i++) {
                GenericBeanDefinition beanDefinition =
                        (GenericBeanDefinition)this.beanFactory.getBeanDefinition(beanNames[i]);
                AppServiceDefinition appServiceDefinition = AppServiceDefinition.build(beanDefinition.getBeanClass());
                registAppServiceDefinition(appServiceDefinition);
            }
        }
    }
}
