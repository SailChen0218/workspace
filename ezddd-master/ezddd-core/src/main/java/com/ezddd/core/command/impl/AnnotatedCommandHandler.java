package com.ezddd.core.command.impl;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.command.*;
import com.ezddd.core.context.CommandContext;
import com.ezddd.core.context.CommandContextHolder;
import com.ezddd.core.repository.RepositoryFactory;
import com.ezddd.core.response.CommandResult;
import com.ezddd.core.spring.EzBeanFactoryPostProcessor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Method;

@EzComponent
public class AnnotatedCommandHandler extends AbstractCommandHandler {
    private static final Logger log = LoggerFactory.getLogger(EzBeanFactoryPostProcessor.class);

    @Autowired
    CommandRegistry commandRegistry;

    @Autowired
    RepositoryFactory repositoryFactory;

    @Override
    public <T> CommandResult<T> handle(Command command) {
        String commandName = command.getClass().getName();
        CommandDefinition commandDefinition = commandRegistry.findCommandDefinition(commandName);
        Assert.notNull(commandDefinition, "can't find the commandDefinition by command:[" + commandName + "]");
        CommandContext commandContext = CommandContextHolder.currentCommandContext();
        commandContext.setCommandDefinition(commandDefinition);

        try {
            if (commandDefinition.getCommandType() == CommandType.CREATE) {
                Constructor constructor = (Constructor)commandDefinition.getMethodOfCommandHandler();
                constructor.newInstance(command);
            } else {
                Method method = (Method) commandDefinition.getMethodOfCommandHandler();
                method.invoke(command);
            }
            return CommandResult.valueOfSuccess();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return CommandResult.valueOfError(e);
        }
    }

}
