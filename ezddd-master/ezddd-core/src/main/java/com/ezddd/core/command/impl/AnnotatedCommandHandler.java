package com.ezddd.core.command.impl;

import com.ezddd.core.aggregate.AggregateWrapper;
import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.command.*;
import com.ezddd.core.context.CommandContext;
import com.ezddd.core.context.CommandContextHolder;
import com.ezddd.core.repository.Repository;
import com.ezddd.core.repository.RepositoryRegistry;
import com.ezddd.core.response.CommandResult;
import com.ezddd.core.spring.EzBeanFactoryPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

@EzComponent
public class AnnotatedCommandHandler extends AbstractCommandHandler {
    private static final Logger log = LoggerFactory.getLogger(AnnotatedCommandHandler.class);

    @Autowired
    CommandRegistry commandRegistry;

    @Autowired
    RepositoryRegistry repositoryRegistry;

    @Override
    public <T> CommandResult<T> handle(Command command) {
        String commandName = command.getClass().getName();
        CommandDefinition commandDefinition = commandRegistry.findCommandDefinition(commandName);
        Assert.notNull(commandDefinition, "can't find the commandDefinition by command:[" + commandName + "]");
        CommandContext commandContext = CommandContextHolder.currentCommandContext();
        commandContext.setCommandDefinition(commandDefinition);

        try {
            if (commandDefinition.getCommandType() == CommandType.CREATE) {
                Constructor constructor = (Constructor) commandDefinition.getMethodOfCommandHandler();
                constructor.newInstance(command);
            } else {
                Class<?> aggregateType = commandDefinition.getAggregateType();
                Repository repository = repositoryRegistry.findRepository(aggregateType);
                if (repository == null) {
                    throw new IllegalArgumentException("repository not found. aggregateType:"
                            + aggregateType.getName());
                }

                Field identifierField = commandDefinition.getIdentifierField();
                Object identifier = identifierField.get(command);
                if (identifier == null) {
                    throw new IllegalArgumentException("target identifier must not be null. command:"
                            + commandDefinition.getCommandName());
                }

                Field versionField = commandDefinition.getVersionField();
                Object version = versionField.get(command);
                if (version == null) {
                    throw new IllegalArgumentException("target version must not be null. command:"
                            + commandDefinition.getCommandName());
                }

                Long versionLong = Long.parseLong(versionField.get(command).toString());
                Object aggregateRoot = repository.load(identifier.toString(), versionLong);

                Method method = (Method) commandDefinition.getMethodOfCommandHandler();
                method.invoke(aggregateRoot, command);
            }
            return CommandResult.valueOfSuccess();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return CommandResult.valueOfError(e);
        }
    }

}
