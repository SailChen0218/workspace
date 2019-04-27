package com.ezddd.app.command;

import com.ezddd.app.registry.CommandExecutorRegistry;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractCommandBus<AppResult> implements CommandBus {
    @Autowired
    protected CommandExecutorRegistry commandExecutorRegistry;
}
