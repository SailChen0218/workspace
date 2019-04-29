package com.ezddd.app.registry;


import com.ezddd.app.command.CommandBus;
import com.ezddd.app.command.CommandHandler;
import com.ezddd.app.command.impl.DefaultCommandBus;
import com.ezddd.common.annotation.EzCommandExecutor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.Assert;

public class CommandDefinition {
    private String command;
    private CommandHandler commandHandler;
    private CommandBus commandBus;

    public static CommandDefinition build(CommandHandler commandHandler, BeanFactory beanFactory) {
        Assert.notNull(commandHandler, "parameter commandHandler must not be null.");

        EzCommandExecutor ezCommandExecutor = commandHandler.getClass().getAnnotation(EzCommandExecutor.class);
        Assert.notNull(ezCommandExecutor, "EzCommandExecutor annotation not found.");

        CommandDefinition commandDefinition = new CommandDefinition();
        commandDefinition.setCommand(ezCommandExecutor.commandType().getName());
        commandDefinition.setCommandHandler(commandHandler);
        if (Object.class.equals(ezCommandExecutor.commandBus())) {
            commandDefinition.setCommandBus(beanFactory.getBean(DefaultCommandBus.class));
        } else {
            commandDefinition.setCommandBus((CommandBus) beanFactory.getBean(ezCommandExecutor.commandBus()));
        }

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
