package com.ezddd.common.registry;

import com.ezddd.common.annotation.EzCommandHandler;
import com.ezddd.common.command.CommandBus;
import com.ezddd.common.command.CommandHandler;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.Assert;

public class CommandDefinition {
    private String command;
    private CommandHandler commandHandler;
    private CommandBus commandBus;

    public static CommandDefinition build(CommandHandler commandHandler, BeanFactory beanFactory) {
        Assert.notNull(commandHandler, "parameter commandHandler must not be null.");

        EzCommandHandler ezCommandHandler = commandHandler.getClass().getAnnotation(EzCommandHandler.class);
        Assert.notNull(ezCommandHandler, "EzCommandHandler annotation not found.");

        CommandDefinition commandDefinition = new CommandDefinition();
//        commandDefinition.setCommand(ezCommandHandler.commandType().getName());
//        commandDefinition.setCommandHandler(commandHandler);
//        if (Object.class.equals(ezCommandHandler.commandBus())) {
//            commandDefinition.setCommandBus(beanFactory.getBean(DefaultCommandBus.class));
//        } else {
//            commandDefinition.setCommandBus((CommandBus) beanFactory.getBean(ezCommandHandler.commandBus()));
//        }

        return commandDefinition;
    }

    public String getCommand() {
        return command;
    }

    public void setCommand(String command) {
        this.command = command;
    }

    public CommandHandler getCommandHandler() {
        return commandHandler;
    }

    public void setCommandHandler(CommandHandler commandHandler) {
        this.commandHandler = commandHandler;
    }

    public CommandBus getCommandBus() {
        return commandBus;
    }

    public void setCommandBus(CommandBus commandBus) {
        this.commandBus = commandBus;
    }
}
