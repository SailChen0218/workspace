package com.ezddd.common.command;

import com.ezddd.common.registry.CommandRegistry;
import org.springframework.beans.factory.annotation.Autowired;

public abstract class AbstractCommandGateway implements CommandGateway {
    @Autowired
    protected CommandRegistry commandRegistry;
}
