package com.ezddd.core.appservice;

import com.ezddd.core.annotation.EzAppMapping;
import com.ezddd.core.annotation.EzAppService;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AppServiceDefinition {
    private String bizCode;

    /**
     * Map of *bizDetailCode, Method*
     */
    private Map<String, Method> bizDetailMethodMap;
    private AppService appService;

    public static AppServiceDefinition build(AppService appService) {
        Assert.notNull(appService, "parameter appService must not be null.");
        AppServiceDefinition appServiceDefinition = new AppServiceDefinition();
        Map<String, Method> bizDetailMethodMap = new HashMap<>();
        Class<?> clazz = appService.getClass();
        EzAppService ezAppService = clazz.getAnnotation(EzAppService.class);
        appServiceDefinition.setBizCode(ezAppService.bizCode());

        Method[] methods = clazz.getMethods();
        for (int i = 0; i < methods.length; i++) {
            EzAppMapping ezAppBizDetails = methods[i].getAnnotation(EzAppMapping.class);
            if (ezAppBizDetails != null) {
                String bizDetailCode = ezAppBizDetails.bizDetailCode();
                if (bizDetailMethodMap.containsKey(bizDetailCode)) {
                    throw new IllegalArgumentException("bizDetailCode already exxists.");
                } else {
                    bizDetailMethodMap.put(bizDetailCode, methods[i]);
                }
            }
        }
        appServiceDefinition.setBizDetailMethodMap(bizDetailMethodMap);
        appServiceDefinition.setAppService(appService);
        return appServiceDefinition;
    }

    public Method findBizDetailMethod(String bizDetailCode) {
        Assert.notNull(bizDetailCode, "parameter bizDetailCode must not be null.");
        return bizDetailMethodMap.get(bizDetailCode);
    }

    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Map<String, Method> getBizDetailMethodMap() {
        return bizDetailMethodMap;
    }

    public void setBizDetailMethodMap(Map<String, Method> bizDetailMethodMap) {
        this.bizDetailMethodMap = bizDetailMethodMap;
    }

    public AppService getAppService() {
        return appService;
    }

    public void setAppService(AppService appService) {
        this.appService = appService;
    }
}