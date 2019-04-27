package com.ezddd.app.registry.impl;

import com.ezddd.app.command.Command;
import com.ezddd.app.command.CommandBus;
import com.ezddd.app.command.CommandExecutor;
import com.ezddd.app.registry.CommandDefinition;
import com.ezddd.app.registry.CommandExecutorRegistry;
import com.ezddd.common.annotation.EzComponent;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.annotation.Order;
import org.springframework.util.Assert;

import java.util.HashMap;
import java.util.Map;

@EzComponent
@Order(1)
public class CommandExecutorRegistryImpl implements CommandExecutorRegistry {
    // <CommandTypeName, CommandExecutor>
    protected static Map<String, CommandDefinition> commandDefinitionHolder = new HashMap<>(16);

    private ConfigurableListableBeanFactory beanFactory;

    @Override
    public void registerCommandExecutor(CommandExecutor commandExecutor) {
        CommandDefinition commandDefinition = CommandDefinition.build(commandExecutor, beanFactory);
        commandDefinitionHolder.put(commandDefinition.getCommand(), commandDefinition);
    }

    @Override
    public CommandExecutor findCommandExecutor(Command command) {
        Assert.notNull(command, "parameter command must not be null.");
        CommandDefinition commandDefinition = commandDefinitionHolder.get(command.getClass().getName());
        Assert.notNull(commandDefinition, "CommandDefinition not found " +
                "with command:" + command.getClass().getName());
        return commandDefinition.getCommandExecutor();
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
        Map<String, CommandExecutor> commandExecutorMap = this.beanFactory.getBeansOfType(CommandExecutor.class);
        for (CommandExecutor commandExecutor: commandExecutorMap.values()) {
            this.registerCommandExecutor(commandExecutor);
        }
    }
}
