package com.ezddd.core.command;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractCommandBus<T> implements CommandBus<T> {
    @Autowired
    protected CommandRegistry commandRegistry;
}
