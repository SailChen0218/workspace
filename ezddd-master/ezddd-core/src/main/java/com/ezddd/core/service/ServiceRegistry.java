package com.ezddd.core.service;

import com.ezddd.core.registry.Registry;

public interface ServiceRegistry extends Registry {
    ServiceDefinition findeServiceDefinition(String interfaceName);
}
