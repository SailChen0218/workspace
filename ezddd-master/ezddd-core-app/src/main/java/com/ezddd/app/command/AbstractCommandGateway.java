package com.ezddd.app.command;

import com.ezddd.app.registry.CommandRegistry;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractCommandGateway implements CommandGateway {
    @Autowired
    protected CommandRegistry commandRegistry;
}
