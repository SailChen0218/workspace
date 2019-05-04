package com.ezddd.core.appservice;

import com.ezddd.core.registry.Registry;

public interface AppServiceRegistry extends Registry {
    AppServiceDefinition findAppServiceDefinition(String bizCode);
    void registAppServiceDefinition(AppServiceDefinition appServiceDefinition);
}
