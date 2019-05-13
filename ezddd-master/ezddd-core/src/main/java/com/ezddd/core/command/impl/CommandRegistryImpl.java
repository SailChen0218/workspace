package com.ezddd.core.command.impl;

import com.ezddd.core.annotation.EzAggregate;
import com.ezddd.core.annotation.EzCommand;
import com.ezddd.core.annotation.EzCommandHandler;
import com.ezddd.core.annotation.EzComponent;
import com.ezddd.core.command.Command;
import com.ezddd.core.command.CommandDefinition;
import com.ezddd.core.command.CommandRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.beans.factory.support.GenericBeanDefinition;

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

    @Override
    public void registry(BeanFactory beanFactory) {
        DefaultListableBeanFactory defaultListableBeanFactory = (DefaultListableBeanFactory) beanFactory;

        String[] commandBeanNames = defaultListableBeanFactory.getBeanNamesForAnnotation(EzCommand.class);
        GenericBeanDefinition commandBeanDefinition = null;
        CommandDefinition commandDefinition = null;
        if (commandBeanNames != null && commandBeanNames.length > 0) {
            for (int i = 0; i < commandBeanNames.length; i++) {
                commandBeanDefinition = (GenericBeanDefinition)defaultListableBeanFactory.getBeanDefinition(commandBeanNames[i]);
                commandDefinition = CommandDefinition.build(commandBeanDefinition.getBeanClass(), beanFactory);
                commandDefinitionHolder.put(commandBeanDefinition.getBeanClassName(), commandDefinition);
//                defaultListableBeanFactory.removeBeanDefinition(commandBeanNames[i]);
            }
        }

        String[] aggregateBeanNames = defaultListableBeanFactory.getBeanNamesForAnnotation(EzAggregate.class);
        GenericBeanDefinition aggregateBeanDefinition = null;
        if (aggregateBeanNames != null && aggregateBeanNames.length > 0) {
            for (int i = 0; i < aggregateBeanNames.length; i++) {
                aggregateBeanDefinition = (GenericBeanDefinition)defaultListableBeanFactory.getBeanDefinition(aggregateBeanNames[i]);
                this.populateAggregateInfo(aggregateBeanDefinition.getBeanClass());
                defaultListableBeanFactory.removeBeanDefinition(aggregateBeanNames[i]);
            }
        }
    }

    private void populateAggregateInfo(Class<?> aggregateType) {
        List<Executable> executableList = new ArrayList<>(8);
        Method[] methods = aggregateType.getMethods();
        if (methods != null) {
            executableList.addAll(Arrays.asList(methods));
        }

        Constructor<?>[] constructors = aggregateType.getConstructors();
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

            commandDefinition.setAggregateType(aggregateType);
            commandDefinition.setMethodOfCommandHandler(method);
        }
    }

    @Override
    public CommandDefinition findCommandDefinition(String commandName) {
        return commandDefinitionHolder.get(commandName);
    }
}
