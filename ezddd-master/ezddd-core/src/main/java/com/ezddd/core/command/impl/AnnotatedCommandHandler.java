package com.ezddd.core.command.impl;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.command.*;
import com.ezddd.core.context.CommandContext;
import com.ezddd.core.context.CommandContextHolder;
import com.ezddd.core.repository.Repository;
import com.ezddd.core.repository.RepositoryProvider;
import com.ezddd.core.response.CommandResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

@EzComponent
public class AnnotatedCommandHandler extends AbstractCommandHandler {

    @Autowired
    CommandRegistry commandRegistry;

    @Autowired
    RepositoryProvider repositoryProvider;

    @Override
    public <T> CommandResult<T> handle(Command command) {
        String commandName = command.getClass().getName();
        CommandDefinition commandDefinition = commandRegistry.findCommandDefinition(commandName);
        Assert.notNull(commandDefinition, "can't find the commandDefinition by command:[" + commandName + "]");

        Repository repository = repositoryProvider.repositoryFor(commandDefinition.getAggregateType());

        CommandContext<T> commandContext = CommandContextHolder.currentCommandContext();
        commandContext.setCommandDefinition(commandDefinition);
        commandContext.setRepository(repository);

        if (commandDefinition.getCommandType() == CommandType.CREATE) {
            Class<?> aggregateType = commandDefinition.getAggregateType();
            Class<?>[] classList = aggregateType.getDeclaredClasses();
        }

        return null;
    }

}
