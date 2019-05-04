package com.ezddd.core.appservice.impl;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.appservice.AppService;
import com.ezddd.core.appservice.AppServiceDefinition;
import com.ezddd.core.appservice.AppServiceRegistry;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@EzComponent
public class AppServiceRegistryImpl implements AppServiceRegistry {
    /**
     * <bizCode, AppServiceDefinition>
     */
    protected static Map<String, AppServiceDefinition> appServiceDefinitionHolder = new HashMap<>(16);
    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public AppServiceDefinition findAppServiceDefinition(String bizCode) {
        Assert.notNull(bizCode, "parameter bizCode must not be null");
        return appServiceDefinitionHolder.get(bizCode);
    }

    @Override
    public void registAppServiceDefinition(AppServiceDefinition appServiceDefinition) {
        Assert.notNull(appServiceDefinition, "parameter appServiceDefinition must not be null");
        if (appServiceDefinitionHolder.containsKey(appServiceDefinition.getBizCode())) {
            throw new IllegalArgumentException("bizCode already exists. bizCode=" + appServiceDefinition.getBizCode());
        }
        appServiceDefinitionHolder.put(appServiceDefinition.getBizCode(), appServiceDefinition);
    }

    @Override
    public void registry(BeanFactory beanFactory) {
        this.beanFactory = (ConfigurableListableBeanFactory)beanFactory;
        Map<String, AppService> appServiceMap = this.beanFactory.getBeansOfType(AppService.class);
        for (AppService appService : appServiceMap.values()) {
            AppServiceDefinition appServiceDefinition = AppServiceDefinition.build(appService);
            registAppServiceDefinition(appServiceDefinition);
        }
    }
}
