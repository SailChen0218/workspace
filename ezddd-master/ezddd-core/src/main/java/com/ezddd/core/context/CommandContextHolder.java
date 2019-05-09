package com.ezddd.core.context;

import com.ezddd.core.context.impl.CommandContextImpl;

public abstract class CommandContextHolder {
    private static final ThreadLocal<CommandContext> commandContextHolder = new ThreadLocal();

    public static void resetCommandContext() {
        commandContextHolder.remove();
    }

    public static void setCommandContext(CommandContext commandContext) {
        if (commandContext == null) {
            resetCommandContext();
        } else {
            commandContextHolder.set(commandContext);
        }
    }

    public static CommandContext getCommandContext() {
        return commandContextHolder.get();
    }

    public static CommandContext currentCommandContext() {
        CommandContext commandContext = getCommandContext();
        if (commandContext == null) {
            commandContext = new CommandContextImpl();
            setCommandContext(commandContext);
        }
        return commandContext;
    }
}
