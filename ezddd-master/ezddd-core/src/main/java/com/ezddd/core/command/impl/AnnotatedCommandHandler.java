package com.ezddd.core.command.impl;

import com.ezddd.core.aggregate.AggregateWrapper;
import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.command.*;
import com.ezddd.core.context.CommandContext;
import com.ezddd.core.context.CommandContextHolder;
import com.ezddd.core.repository.Repository;
import com.ezddd.core.repository.RepositoryProvider;
import com.ezddd.core.response.CommandResult;
import com.ezddd.core.spring.EzBeanFactoryPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.lang.reflect.Method;

@EzComponent
public class AnnotatedCommandHandler extends AbstractCommandHandler {
    private static final Logger log = LoggerFactory.getLogger(EzBeanFactoryPostProcessor.class);

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
            Class<?>[] classArray = aggregateType.getDeclaredClasses();
            try {
                Method method = classArray[0].getMethod("createAggregate", command.getClass());
                Object result = method.invoke(null, command);
                AggregateWrapper aggregateWrapper = new AggregateWrapper(result);
                repository.add(aggregateWrapper);
                return CommandResult.valueOfSuccess();
            } catch (Exception e) {
                log.error(e.getMessage(), e);
                return CommandResult.valueOfError(e);
            }
        }

        return null;
    }

}
