package com.ezddd.core.appservice;

import com.ezddd.core.annotation.EzAppService;
import com.ezddd.core.annotation.EzCommand;
import com.ezddd.core.annotation.EzCommandMapping;
import com.ezddd.core.annotation.EzQueryMapping;
import org.springframework.util.Assert;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class AppServiceDefinition {
    private String bizCode;

    /**
     * Map of *bizDetailCode, Method*
     */
    private Map<String, MethodDefiniton> bizDetailMethodMap;

    private Class<?> appServiceType;
    private AppService appService;

    public static AppServiceDefinition build(Class<?> appServiceType) {
        Assert.notNull(appServiceType, "parameter appServiceType must not be null.");
        AppServiceDefinition appServiceDefinition = new AppServiceDefinition();
        Map<String, MethodDefiniton> bizDetailMethodMap = new HashMap<>();
        EzAppService ezAppService = appServiceType.getAnnotation(EzAppService.class);
        appServiceDefinition.setBizCode(ezAppService.bizCode());
        Method[] methods = appServiceType.getMethods();
        for (int i = 0; i < methods.length; i++) {
            String bizDetailCode = getBizDetailCodeOfMethod(methods[i]);
            if (bizDetailCode == null) {
                continue;
            }
            if (bizDetailMethodMap.containsKey(bizDetailCode)) {
                throw new IllegalArgumentException("bizDetailCode already exxists. appServiceType:"
                        + appServiceType.getName() + ", bizDetailCode:" + bizDetailCode);
            } else {
                MethodDefiniton methodDefiniton = new MethodDefiniton(methods[i]);
                bizDetailMethodMap.put(bizDetailCode, methodDefiniton);
            }
        }
        appServiceDefinition.setBizDetailMethodMap(bizDetailMethodMap);
        appServiceDefinition.setAppServiceType(appServiceType);
        return appServiceDefinition;
    }

    public MethodDefiniton findMethodDefinition(String bizDetailCode) {
        Assert.notNull(bizDetailCode, "parameter bizDetailCode must not be null.");
        return bizDetailMethodMap.get(bizDetailCode);
    }

    private static String getBizDetailCodeOfMethod(Method method) {
        String bizDetailCode = null;
        EzCommandMapping commandMapping = method.getAnnotation(EzCommandMapping.class);
        if (commandMapping != null) {
            bizDetailCode = commandMapping.bizDetailCode();
            if (bizDetailCode == null || "".equals(bizDetailCode)) {
                bizDetailCode = method.getName();
            }
        } else {
            EzQueryMapping queryMapping = method.getAnnotation(EzQueryMapping.class);
            if (queryMapping != null) {
                bizDetailCode = queryMapping.bizDetailCode();
                if (bizDetailCode == null || "".equals(bizDetailCode)) {
                    bizDetailCode = method.getName();
                }
            } else {
                return null;
            }
        }

        return bizDetailCode;
    }



    public String getBizCode() {
        return bizCode;
    }

    public void setBizCode(String bizCode) {
        this.bizCode = bizCode;
    }

    public Map<String, MethodDefiniton> getBizDetailMethodMap() {
        return bizDetailMethodMap;
    }

    public void setBizDetailMethodMap(Map<String, MethodDefiniton> bizDetailMethodMap) {
        this.bizDetailMethodMap = bizDetailMethodMap;
    }

    public AppService getAppService() {
        return appService;
    }

    public void setAppService(AppService appService) {
        this.appService = appService;
    }

    public Class<?> getAppServiceType() {
        return appServiceType;
    }

    public void setAppServiceType(Class<?> appServiceType) {
        this.appServiceType = appServiceType;
    }
}