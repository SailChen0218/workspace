package com.ezddd.app.registry;

import com.ezddd.app.command.Command;
import com.ezddd.app.command.CommandBus;
import com.ezddd.app.command.CommandHandler;
import com.ezddd.common.registry.Registry;

public interface CommandRegistry extends Registry {
    void registerCommandExecutor(CommandHandler commandHandler);
    CommandHandler findCommandExecutor(Command command);
    CommandBus findCommandBus(Command command);
}
