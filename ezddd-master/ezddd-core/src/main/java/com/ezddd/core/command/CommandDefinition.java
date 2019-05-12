package com.ezddd.core.command;

import com.ezddd.core.annotation.EzCommand;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.Assert;

import java.lang.reflect.Executable;

public class CommandDefinition {
    private String domain;
    private int commandType;
    private String commandName;
    private CommandBus commandBus;
    private Class<?> aggregateType;
    private Executable methodOfCommandHandler;

    public static CommandDefinition build(Class<?> commandType, BeanFactory beanFactory) {
        Assert.notNull(commandType, "parameter commandType must not be null.");
        CommandDefinition commandDefinition = new CommandDefinition();
        EzCommand ezCommand = commandType.getAnnotation(EzCommand.class);
        commandDefinition.domain = ezCommand.domain();
        commandDefinition.commandType = ezCommand.commandType();
        commandDefinition.commandBus = beanFactory.getBean(ezCommand.commandBus(), CommandBus.class);
        return commandDefinition;
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

}
