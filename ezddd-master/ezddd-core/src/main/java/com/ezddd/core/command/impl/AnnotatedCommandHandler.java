package com.ezddd.core.command.impl;

import com.ezddd.core.aggregate.AggregateNotFoundException;
import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.command.*;
import com.ezddd.core.context.CommandContext;
import com.ezddd.core.context.CommandContextHolder;
import com.ezddd.core.repository.Repository;
import com.ezddd.core.repository.RepositoryRegistry;
import com.ezddd.core.response.CommandResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@EzComponent
public class AnnotatedCommandHandler extends AbstractCommandHandler {
    private static final Logger log = LoggerFactory.getLogger(AnnotatedCommandHandler.class);

    @Autowired
    CommandRegistry commandRegistry;

    @Autowired
    RepositoryRegistry repositoryRegistry;

    @Override
    public CommandResult<?> handle(Command command) {
        String commandName = command.getClass().getName();
        CommandDefinition commandDefinition = commandRegistry.findCommandDefinition(commandName);
        Assert.notNull(commandDefinition, "can't find the commandDefinition by command:[" + commandName + "]");
        CommandContext commandContext = CommandContextHolder.currentCommandContext();
        commandContext.setCommandDefinition(commandDefinition);
        Object result = null;
        try {
            if (commandDefinition.getCommandType() == CommandType.CREATE) {
                Constructor constructor = (Constructor) commandDefinition.getMethodOfCommandHandler();
                constructor.newInstance(command);
            } else {
                Class<?> aggregateType = commandDefinition.getAggregateType();
                Repository repository = repositoryRegistry.findRepository(aggregateType);
                if (repository == null) {
                    return CommandResult.valueOfError("repository not found. aggregateType:" + aggregateType.getName());
                }

                Object identifier = null;
                Field identifierField = commandDefinition.getIdentifierField();
                if (identifierField == null) {
                    return CommandResult.valueOfError("target identifier must not be null. command:"
                            + commandDefinition.getCommandName());
                } else {
                    identifierField.setAccessible(true);
                    identifier = identifierField.get(command);
                }

                Object aggregateRoot = null;
                Field versionField = commandDefinition.getVersionField();
                if (versionField != null) {
                    versionField.setAccessible(true);
                    Object version = versionField.get(command);
                    if (version == null) {
                        return CommandResult.valueOfError("target version must not be null. command:"
                                + commandDefinition.getCommandName());
                    }

                    Long versionLong = Long.parseLong(versionField.get(command).toString());
                    aggregateRoot = repository.load(identifier.toString(), versionLong);
                } else {
                    aggregateRoot = repository.load(identifier.toString());
                }

                Method method = (Method) commandDefinition.getMethodOfCommandHandler();
                result = method.invoke(aggregateRoot, command);
            }
            return CommandResult.valueOfSuccess(result);
        } catch (IllegalAccessException e) {
            log.error("IllegalAccessException occurred when execution command:" + commandName, e);
            return CommandResult.valueOfError(e.getCause());
        } catch (InstantiationException e) {
            log.error("InstantiationException occurred when execution command:" + commandName, e);
            return CommandResult.valueOfError(e.getCause());
        } catch (InvocationTargetException e) {
            log.error("InvocationTargetException occurred when execution command:" + commandName, e);
            return CommandResult.valueOfError(e.getTargetException());
        } catch (AggregateNotFoundException e) {
            return CommandResult.valueOfError(e);
        }
    }
}
