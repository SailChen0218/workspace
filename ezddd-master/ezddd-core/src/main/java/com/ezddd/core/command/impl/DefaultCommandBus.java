package com.ezddd.core.command.impl;

import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.command.AbstractCommandBus;
import com.ezddd.core.command.Command;
import com.ezddd.core.command.CommandDefinition;
import com.ezddd.core.command.CommandType;
import com.ezddd.core.repository.Repository;
import com.ezddd.core.repository.RepositoryProvider;
import com.ezddd.core.response.AppResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.Assert;

import java.lang.reflect.Executable;

@EzComponent
public class DefaultCommandBus extends AbstractCommandBus {

    @Autowired
    RepositoryProvider repositoryProvider;

    @Override
    public AppResult dispatch(Command cmd) {
        String commandName = cmd.getClass().getSimpleName();
        CommandDefinition commandDefinition = commandRegistry.findCommandDefinition(commandName);
        Assert.notNull(commandDefinition, "can't find the commandDefinition by command:[" + commandName + "]");

        Repository repository = repositoryProvider.repositoryFor(commandDefinition.getAggregateType());
        if (commandDefinition.getCommandType() == CommandType.CREATE) {
            Class<?> factoryClazz = commandDefinition.getAggregateType().getDeclaringClass();
            Executable executable = commandDefinition.getMethodOfCommandHandler();
//            if () {
//
//            }
//            factoryClazz.
        }
        return AppResult.valueOfSuccess();
//        commandDefinition
//
//        if (commandHandler == null) {
//            return AppResult.valueOfError("001");
//        } else {
//            return commandHandler.execute(cmd);
//        }
    }

}
