package com.ezddd.app.registry.impl;

import com.ezddd.app.command.Command;
import com.ezddd.app.command.CommandBus;
import com.ezddd.app.command.CommandHandler;
import com.ezddd.app.registry.CommandDefinition;
import com.ezddd.app.registry.CommandRegistry;
import com.ezddd.common.annotation.EzComponent;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@EzComponent
public class CommandRegistryImpl implements CommandRegistry {
    // <CommandTypeName, CommandHandler>
    protected static Map<String, CommandDefinition> commandDefinitionHolder = new HashMap<>(16);

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void registerCommandExecutor(CommandHandler commandHandler) {
        CommandDefinition commandDefinition = CommandDefinition.build(commandHandler, beanFactory);
        commandDefinitionHolder.put(commandDefinition.getCommand(), commandDefinition);
    }

    @Override
    public CommandHandler findCommandExecutor(Command command) {
        Assert.notNull(command, "parameter command must not be null.");
        CommandDefinition commandDefinition = commandDefinitionHolder.get(command.getClass().getName());
        Assert.notNull(commandDefinition, "CommandDefinition not found " +
                "with command:" + command.getClass().getName());
        return commandDefinition.getCommandHandler();
    }

    @Override
    public CommandBus findCommandBus(Command command) {
        Assert.notNull(command, "parameter command must not be null.");
        CommandDefinition commandDefinition = commandDefinitionHolder.get(command.getClass().getName());
        Assert.notNull(commandDefinition, "CommandDefinition not found " +
                "with command:" + command.getClass().getName());
        return commandDefinition.getCommandBus();
    }

    @Override
    public void registry(BeanFactory beanFactory) {
        this.beanFactory = (ConfigurableListableBeanFactory)beanFactory;
        Map<String, CommandHandler> commandExecutorMap = this.beanFactory.getBeansOfType(CommandHandler.class);
        for (CommandHandler commandHandler : commandExecutorMap.values()) {
            this.registerCommandExecutor(commandHandler);
        }
    }
}
