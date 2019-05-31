package com.ezddd.core.command.impl;

import com.ezddd.core.annotation.EzCommand;
import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.command.CommandBus;
import com.ezddd.core.command.CommandBusRegistry;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

import java.util.HashMap;
import java.util.Map;

@EzComponent
public class CommandBusRegistryImpl implements CommandBusRegistry {

    /**
     * Map of *CommandClassName, CommandBusDefinition*
     */
    protected static Map<String, CommandBusDefinition> commandBusDefinitionHolder = new HashMap<>(16);


    private DefaultListableBeanFactory beanFactory;

    @Override
    public CommandBus findCommandBus(String commandName) {
        if (commandBusDefinitionHolder.containsKey(commandName)) {
            CommandBusDefinition commandBusDefinition = commandBusDefinitionHolder.get(commandName);
            CommandBus commandBus = (CommandBus) this.beanFactory.getBean(commandBusDefinition.getCommandBusName());
            commandBusDefinition.setCommandBus(commandBus);
            return commandBus;
        } else {
            throw new IllegalArgumentException("CommandBus not found, commandName:[" + commandName + "]. ");
        }
    }

    @Override
    public void registry(BeanFactory beanFactory) {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
        String[] beanNames = this.beanFactory.getBeanNamesForAnnotation(EzCommand.class);
        GenericBeanDefinition beanDefinition = null;
        if (beanNames != null && beanNames.length > 0) {
            for (int i = 0; i < beanNames.length; i++) {
                beanDefinition = (GenericBeanDefinition) this.beanFactory.getBeanDefinition(beanNames[i]);
                EzCommand ezCommand = beanDefinition.getBeanClass().getAnnotation(EzCommand.class);
                CommandBusDefinition commandBusDefinition = new CommandBusDefinition();
                commandBusDefinition.setCommandBusName(ezCommand.commandBus());
                commandBusDefinitionHolder.put(beanDefinition.getBeanClassName(), commandBusDefinition);
            }
        }
    }

    private class CommandBusDefinition {
        private String commandBusName;
        private CommandBus commandBus;

        public CommandBus getCommandBus() {
            return commandBus;
        }

        public void setCommandBus(CommandBus commandBus) {
            this.commandBus = commandBus;
        }

        public String getCommandBusName() {
            return commandBusName;
        }

        public void setCommandBusName(String commandBusName) {
            this.commandBusName = commandBusName;
        }
    }

}
