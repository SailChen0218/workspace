package com.ezddd.core.command;

import com.ezddd.core.annotation.EzCommand;
import com.ezddd.core.utils.ClassUtil;
import com.ezddd.core.utils.CommandUtil;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.Assert;

import java.lang.reflect.Executable;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class CommandDefinition {
    private String domain;
    private int commandType;
    private String commandName;
    private CommandBus commandBus;
    private String commandBusName;
    private Class<?> aggregateType;
    private Executable methodOfCommandHandler;
    private Field identifierField;
    private Field versionField;

    public static CommandDefinition build(Class<?> commandType, BeanFactory beanFactory) {
        Assert.notNull(commandType, "parameter commandType must not be null.");
        CommandDefinition commandDefinition = new CommandDefinition();
        EzCommand ezCommand = commandType.getAnnotation(EzCommand.class);
        commandDefinition.domain = ezCommand.domain();
        commandDefinition.commandType = ezCommand.commandType();
        commandDefinition.commandBusName = ezCommand.commandBus();
        List<Field> fieldList = new ArrayList<>();
        ClassUtil.findFiledsIncludeSuperClass(commandType, fieldList);
        commandDefinition.identifierField = CommandUtil.getIdentifierField(fieldList);
        commandDefinition.versionField = CommandUtil.getVersionField(fieldList);
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

    public String getCommandBusName() {
        return commandBusName;
    }

    public void setCommandBusName(String commandBusName) {
        this.commandBusName = commandBusName;
    }

    public Field getIdentifierField() {
        return identifierField;
    }

    public void setIdentifierField(Field identifierField) {
        this.identifierField = identifierField;
    }

    public Field getVersionField() {
        return versionField;
    }

    public void setVersionField(Field versionField) {
        this.versionField = versionField;
    }
}
