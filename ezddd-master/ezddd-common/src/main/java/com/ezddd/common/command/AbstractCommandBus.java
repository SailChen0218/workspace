package com.ezddd.common.command;

import com.ezddd.common.registry.CommandRegistry;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractCommandBus<AppResult> implements CommandBus {
    @Autowired
    protected CommandRegistry commandRegistry;
}
