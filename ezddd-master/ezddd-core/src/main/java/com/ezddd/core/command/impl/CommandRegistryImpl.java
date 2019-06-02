package com.ezddd.core.command.impl;

import com.ezddd.core.annotation.EzAggregate;
import com.ezddd.core.annotation.EzCommand;
import com.ezddd.core.annotation.EzCommandHandler;
import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.command.Command;
import com.ezddd.core.command.CommandBus;
import com.ezddd.core.command.CommandDefinition;
import com.ezddd.core.command.CommandRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

@EzComponent
public class CommandRegistryImpl implements CommandRegistry {
    private static final Logger log = LoggerFactory.getLogger(CommandRegistryImpl.class);

    /**
     * Map of *CommandTypeName, CommandHandler*
     */
    protected static Map<String, CommandDefinition> commandDefinitionHolder = new HashMap<>(16);

    private DefaultListableBeanFactory beanFactory;

    @Override
    public void registry(BeanFactory beanFactory) {
        this.beanFactory = (DefaultListableBeanFactory) beanFactory;
        String[] commandBeanNames = this.beanFactory.getBeanNamesForAnnotation(EzCommand.class);
        GenericBeanDefinition commandBeanDefinition = null;
        CommandDefinition commandDefinition = null;
        if (commandBeanNames != null && commandBeanNames.length > 0) {
            for (int i = 0; i < commandBeanNames.length; i++) {
                commandBeanDefinition = (GenericBeanDefinition)this.beanFactory.getBeanDefinition(commandBeanNames[i]);
                commandDefinition = CommandDefinition.build(commandBeanDefinition.getBeanClass(), beanFactory);
                commandDefinitionHolder.put(commandBeanDefinition.getBeanClassName(), commandDefinition);
            }
        }

        String[] aggregateBeanNames = this.beanFactory.getBeanNamesForAnnotation(EzAggregate.class);
        GenericBeanDefinition aggregateBeanDefinition = null;
        if (aggregateBeanNames != null && aggregateBeanNames.length > 0) {
            for (int i = 0; i < aggregateBeanNames.length; i++) {
                aggregateBeanDefinition = (GenericBeanDefinition)this.beanFactory.getBeanDefinition(aggregateBeanNames[i]);
                try {
                    Class<?> aggregateType = Class.forName(aggregateBeanDefinition.getBeanClass().getName());
                    this.populateAggregateInfo(aggregateType);
                } catch (ClassNotFoundException e) {
                    throw new RuntimeException(e.getMessage(), e);
                }
            }
        }
    }

    private void populateAggregateInfo(Class<?> aggregateType) {
        List<Executable> executableList = new ArrayList<>(8);
        Method[] methods = aggregateType.getDeclaredMethods();
        if (methods != null) {
            executableList.addAll(Arrays.asList(methods));
        }

        Constructor<?>[] constructors = aggregateType.getDeclaredConstructors();
        if (constructors != null) {
            executableList.addAll(Arrays.asList(constructors));
        }

        for(Executable executable :executableList) {
            resolveExecutable(aggregateType, executable);
        }
    }

    private void resolveExecutable(Class<?> aggregateType, Executable method) {
        EzCommandHandler ezCommandHandler = method.getAnnotation(EzCommandHandler.class);
        if (ezCommandHandler != null) {
            Parameter[] parameters = method.getParameters();
            if (parameters == null || parameters.length != 1) {
                throw new IllegalArgumentException("There must be one and only one command type parameter of " +
                        "commandHandler:[" + method.getName() + "]. ");
            }

            Class<?> commandType = parameters[0].getType();
            if (!Command.class.isAssignableFrom(commandType)) {
                throw new IllegalArgumentException("There must be one command type parameter of " +
                        "commandHandler:[" + method.getName() + "]. ");
            }

            if (!commandDefinitionHolder.containsKey(commandType.getName())) {
                throw new IllegalArgumentException("CommandHandler did not find the corresponding command. " +
                        "aggregateType:[" + aggregateType.getName() + "], method:[" + method.getName() + "]. ");
            }

            CommandDefinition commandDefinition = commandDefinitionHolder.get(commandType.getName());
            if (commandDefinition.getAggregateType() != null) {
                throw new IllegalArgumentException("CommandHandler has already exist. " +
                        "new-aggregateType:[" + aggregateType.getName() + "], new-method:[" + method.getName() + "]. " +
                        "old-aggregateType:[" + commandDefinition.getAggregateType().getName() + "], old-method:[" +
                        commandDefinition.getMethodOfCommandHandler().getName() + "]. ");
            }

            commandDefinition.setCommandName(commandType.getName());
            commandDefinition.setAggregateType(aggregateType);
            commandDefinition.setMethodOfCommandHandler(method);
        }
    }

    @Override
    public CommandDefinition findCommandDefinition(String commandName) {
        if (!commandDefinitionHolder.containsKey(commandName)) {
            throw new IllegalArgumentException("Can not found the CommandDefinition of command:[" +
                    commandName + "]. ");
        } else {
            CommandDefinition commandDefinition = commandDefinitionHolder.get(commandName);
            Assert.notNull(commandDefinition.getAggregateType(),
                    "The AggregateType is null. CommandDefinition:[" + commandName + "]. ");
            Assert.notNull(commandDefinition.getMethodOfCommandHandler(),
                    "The MethodOfCommandHandler is null. CommandDefinition:[" + commandName + "]. ");

            if (commandDefinition.getCommandBus() == null) {
                CommandBus commandBus = (CommandBus) beanFactory.getBean(commandDefinition.getCommandBusName());
                if (commandBus == null) {
                    throw new IllegalArgumentException("The bean fo CommandBus not found, CommandDefinition:[" +
                            commandName + "]. ");
                } else {
                    commandDefinition.setCommandBus(commandBus);
                }
            }

            return commandDefinition;
        }
    }
}
