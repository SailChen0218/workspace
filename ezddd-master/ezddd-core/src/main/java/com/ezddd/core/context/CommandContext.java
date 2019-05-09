package com.ezddd.core.context;

import com.ezddd.core.command.CommandDefinition;
import com.ezddd.core.repository.Repository;

public interface CommandContext<T> extends Context {
    Repository getRepository();
    void setRepository(Repository repository);
    void setCommandDefinition(CommandDefinition commandDefinition);
    CommandDefinition getCommandDefinition();
    void setResponse(T value);
    T getResponse();
}
