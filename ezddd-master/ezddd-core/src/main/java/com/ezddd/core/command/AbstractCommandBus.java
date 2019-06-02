package com.ezddd.core.command;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractCommandBus implements CommandBus {
    @Autowired
    protected CommandRegistry commandRegistry;
}
