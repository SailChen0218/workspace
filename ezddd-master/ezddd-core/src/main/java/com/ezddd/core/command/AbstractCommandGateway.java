package com.ezddd.core.command;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractCommandGateway implements CommandGateway {
    @Autowired
    protected CommandRegistry commandRegistry;
}
