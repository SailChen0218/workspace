package com.ezddd.app.registry;


import com.ezddd.app.command.CommandBus;
import com.ezddd.app.command.CommandExecutor;
import com.ezddd.app.command.impl.DefaultCommandBus;
import com.ezddd.common.annotation.EzCommandExecutor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.util.Assert;

public class CommandDefinition {
    private String command;
    private CommandExecutor commandExecutor;
    private CommandBus commandBus;

    public static CommandDefinition build(CommandExecutor commandExecutor, BeanFactory beanFactory) {
        Assert.notNull(commandExecutor, "parameter commandExecutor must not be null.");

        EzCommandExecutor ezCommandExecutor = commandExecutor.getClass().getAnnotation(EzCommandExecutor.class);
        Assert.notNull(ezCommandExecutor, "EzCommandExecutor annotation not found.");

        CommandDefinition commandDefinition = new CommandDefinition();
        commandDefinition.setCommand(ezCommandExecutor.commandType().getName());
        commandDefinition.setCommandExecutor(commandExecutor);
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

    public CommandExecutor getCommandExecutor() {
        return commandExecutor;
    }

    public void setCommandExecutor(CommandExecutor commandExecutor) {
        this.commandExecutor = commandExecutor;
    }

    public CommandBus getCommandBus() {
        return commandBus;
    }

    public void setCommandBus(CommandBus commandBus) {
        this.commandBus = commandBus;
    }
}
