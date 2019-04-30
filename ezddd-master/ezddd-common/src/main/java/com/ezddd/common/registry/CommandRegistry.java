package com.ezddd.common.registry;

import com.ezddd.common.command.Command;
import com.ezddd.common.command.CommandBus;
import com.ezddd.common.command.CommandHandler;

public interface CommandRegistry extends Registry {
    void registerCommandExecutor(CommandHandler commandHandler);
    CommandHandler findCommandExecutor(Command command);
    CommandBus findCommandBus(Command command);
}
