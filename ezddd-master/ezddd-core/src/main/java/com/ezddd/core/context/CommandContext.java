package com.ezddd.core.context;

import com.ezddd.core.command.CommandDefinition;
import com.ezddd.core.repository.Repository;

public interface CommandContext<T> extends Context {
    void setCommandDefinition(CommandDefinition commandDefinition);
    CommandDefinition getCommandDefinition();
    void setAggregateRoot(T value);
    T getAggregateRoot();
}
