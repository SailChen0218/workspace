package com.ezddd.app.registry;

import com.ezddd.app.command.Command;
import com.ezddd.app.command.CommandBus;
import com.ezddd.app.command.CommandExecutor;
import com.ezddd.common.registry.Registry;

public interface CommandExecutorRegistry extends Registry {
    void registerCommandExecutor(CommandExecutor commandExecutor);
    CommandExecutor findCommandExecutor(Command command);
    CommandBus findCommandBus(Command command);
}
