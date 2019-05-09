package com.ezddd.core.command;

import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractCommandGateway<T> implements CommandGateway<T> {
    @Autowired
    protected CommandRegistry commandRegistry;
}
