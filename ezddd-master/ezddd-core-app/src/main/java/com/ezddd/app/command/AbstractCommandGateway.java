package com.ezddd.app.command;

import com.ezddd.app.registry.CommandExecutorRegistry;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractCommandGateway implements CommandGateway {
    @Autowired
    protected CommandExecutorRegistry commandExecutorRegistry;
}
