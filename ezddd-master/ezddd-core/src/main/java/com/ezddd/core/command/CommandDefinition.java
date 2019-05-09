package com.ezddd.core.command;

import com.ezddd.core.annotation.EzCommand;
import com.ezddd.core.annotation.EzCommandHandler;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.*;

public class CommandDefinition {
    private String domain;
    private int commandType;
    private String commandName;
    private CommandBus commandBus;
    private Class<?> aggregateType;
    private Executable methodOfCommandHandler;
    private int grade;

    public static Map<String, CommandDefinition> build(Class<?> aggregateType, BeanFactory beanFactory) {
        Assert.notNull(aggregateType, "parameter aggregateType must not be null.");
        Map<String, CommandDefinition> commandDefinitionMapAll = new HashMap<>(8);
        Map<String, CommandDefinition> commandDefinitionMap = null;
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
            commandDefinitionMap = resolveCommandDefinition(aggregateType, executable, beanFactory);
            if (!CollectionUtils.isEmpty(commandDefinitionMap)) {
                commandDefinitionMapAll.putAll(commandDefinitionMap);
            }
        }

        return commandDefinitionMapAll;
    }

    private static Map<String, CommandDefinition> resolveCommandDefinition(
            Class<?> aggregateType, Executable method, BeanFactory beanFactory) {
        Map<String, CommandDefinition> commandDefinitionMap = null;
        EzCommandHandler ezCommandHandler = method.getAnnotation(EzCommandHandler.class);
        if (ezCommandHandler != null) {
            Parameter[] parameters = method.getParameters();
            if (parameters == null || parameters.length != 1) {
                throw new IllegalArgumentException("There must be on and only one command parameter of " +
                        "commandHandler:[" + method.getName() + "].");
            } else {
                Class<?> commandClazz = parameters[0].getType();
                if (commandDefinitionMap == null) {
                    commandDefinitionMap = new HashMap<>();
                }

                if (commandDefinitionMap.containsKey(commandClazz.getSimpleName())) {
                    throw new IllegalArgumentException("command:[" + commandClazz.getName()
                            + "] has already exist.");
                }

                CommandDefinition commandDefinition = new CommandDefinition();
                commandDefinition.commandName = commandClazz.getName();
                EzCommand ezCommand = commandClazz.getAnnotation(EzCommand.class);
                commandDefinition.domain = ezCommand.domain();
                commandDefinition.commandType = ezCommand.commandType();
                commandDefinition.commandBus = beanFactory.getBean(ezCommand.commandBus(), CommandBus.class);
                commandDefinition.aggregateType = aggregateType;
                if (method instanceof Method) {
                    commandDefinition.methodOfCommandHandler = (Method) method;
                } else {
                    commandDefinition.methodOfCommandHandler = (Constructor) method;
                }
                commandDefinition.grade = ezCommand.grade();
                commandDefinitionMap.put(commandDefinition.commandName, commandDefinition);
            }
        }
        return commandDefinitionMap;
    }

    public String getCommandName() {
        return commandName;
    }

    public CommandBus getCommandBus() {
        return commandBus;
    }

    public Class<?> getAggregateType() {
        return aggregateType;
    }

    public String getDomain() {
        return domain;
    }

    public int getCommandType() {
        return commandType;
    }

    public Executable getMethodOfCommandHandler() {
        return methodOfCommandHandler;
    }

    public void setMethodOfCommandHandler(Executable methodOfCommandHandler) {
        this.methodOfCommandHandler = methodOfCommandHandler;
    }

    public int getGrade() {
        return grade;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }

    public void setCommandType(int commandType) {
        this.commandType = commandType;
    }

    public void setCommandName(String commandName) {
        this.commandName = commandName;
    }

    public void setCommandBus(CommandBus commandBus) {
        this.commandBus = commandBus;
    }

    public void setAggregateType(Class<?> aggregateType) {
        this.aggregateType = aggregateType;
    }

    public void setGrade(int grade) {
        this.grade = grade;
    }
}
