package com.ezddd.app.registry;

import com.ezddd.common.registry.Registry;

public interface AppServiceRegistry extends Registry {
    AppServiceDefinition findAppServiceDefinition(String bizCode);
    void registAppServiceDefinition(AppServiceDefinition appServiceDefinition);
}
