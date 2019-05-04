package com.ezddd.core.command;

import com.ezddd.core.annotation.EzCommand;
import com.ezddd.core.annotation.EzCommandHandler;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.Assert;

import java.lang.reflect.Constructor;
import java.lang.reflect.Executable;
import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.HashMap;
import java.util.Map;

public class CommandDefinition {
    private String domain;
    private int commandType;
    private String commandName;
    private CommandBus commandBus;
    private Class<?> aggregateType;
    private Method methodOfHandler;
    private Constructor constructorOfHandler;
    private int grade;

    public static Map<String, CommandDefinition> build(Class<?> aggregateType, BeanFactory beanFactory) {
        Assert.notNull(aggregateType, "parameter aggregateType must not be null.");
        Map<String, CommandDefinition> commandDefinitionMap = null;
        Method[] methods = aggregateType.getMethods();
        if (methods != null && methods.length > 0) {
            for (int i = 0; i < methods.length; i++) {
                resolveCommandDefinition(commandDefinitionMap, aggregateType, methods[i], beanFactory);
            }
        }

        Constructor<?>[] constructors = aggregateType.getConstructors();
        if (constructors != null && constructors.length > 0) {
            for (int i = 0; i < constructors.length; i++) {
                resolveCommandDefinition(commandDefinitionMap, aggregateType, constructors[i], beanFactory);
            }
        }

        return commandDefinitionMap;
    }

    private static void resolveCommandDefinition(Map<String, CommandDefinition> commandDefinitionMap,
                                                 Class<?> aggregateType,
                                                 Executable method,
                                                 BeanFactory beanFactory) {
        EzCommandHandler ezCommandHandler = method.getAnnotation(EzCommandHandler.class);
        if (ezCommandHandler != null) {
            Parameter[] parameters = method.getParameters();
            if (parameters == null || parameters.length != 1) {
                throw new IllegalArgumentException("There must be on and only one command parameter of " +
                        "commandHandler:[" + method.getName() + "].");
            } else {
                Class<?> commandClazz = parameters[0].getClass();
                if (commandDefinitionMap == null) {
                    commandDefinitionMap = new HashMap<>();
                }

                if (commandDefinitionMap.containsKey(commandClazz.getSimpleName())) {
                    throw new IllegalArgumentException("command:[" + commandClazz.getSimpleName()
                            + "] has already exist.");
                }

                CommandDefinition commandDefinition = new CommandDefinition();
                commandDefinition.commandName = commandClazz.getSimpleName();
                EzCommand ezCommand = commandClazz.getAnnotation(EzCommand.class);
                commandDefinition.domain = ezCommand.domain();
                commandDefinition.commandType = ezCommand.commandType();
                commandDefinition.commandBus = beanFactory.getBean(ezCommand.commandBus(), CommandBus.class);
                commandDefinition.aggregateType = aggregateType;
                if (method instanceof Method) {
                    commandDefinition.methodOfHandler = (Method) method;
                } else {
                    commandDefinition.constructorOfHandler = (Constructor) method;
                }
                commandDefinition.grade = ezCommand.grade();
                commandDefinitionMap.put(commandDefinition.commandName, commandDefinition);
            }
        }
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

    public Method getMethodOfHandler() {
        return methodOfHandler;
    }

    public Constructor getConstructorOfHandler() {
        return constructorOfHandler;
    }

    public int getGrade() {
        return grade;
    }
}
